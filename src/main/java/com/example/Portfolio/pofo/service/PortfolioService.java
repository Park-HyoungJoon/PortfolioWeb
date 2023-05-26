package com.example.Portfolio.pofo.service;

import com.example.Portfolio.member.entity.Member;
import com.example.Portfolio.member.service.MemberService;
import com.example.Portfolio.pagination.Paged;
import com.example.Portfolio.pagination.Paging;
import com.example.Portfolio.pofo.dto.PofoAddDto;
import com.example.Portfolio.pofo.entity.Portfolio;
import com.example.Portfolio.pofo.entity.PortfolioCareer;
import com.example.Portfolio.pofo.repository.PortfolioCareerRepository;
import com.example.Portfolio.pofo.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {
	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PortfolioCareerRepository portfolioCareerRepository;

	public Portfolio savePofo(PofoAddDto pofoAddDto, Long id) {
		Member member = memberService.getMemberById(id);
		member = pofoAddDto.pofoToMember(pofoAddDto, member);
		memberService.save(member);
		pofoAddDto.setMember(member);
		Portfolio portfolio = pofoAddDto.createPortfolio();
		if (pofoAddDto.getExistPofo() != null) {
			portfolio.setId(pofoAddDto.getExistPofo());
		}
		Portfolio portfolio2 = portfolioRepository.save(portfolio);
		try {
			List<String> items = pofoAddDto.itemToCareer(pofoAddDto.getLicense(), pofoAddDto.getCareer(),
					pofoAddDto.getSkill());
			for (String item : items) {
				PortfolioCareer portfolioCareer = new PortfolioCareer();
				portfolioCareer.setItem(item);
				portfolioCareer.setPortfolio(portfolio2);
				portfolioCareerRepository.save(portfolioCareer);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return portfolio2;

	}

	public Paged<Portfolio> getPage(int pageNumber, int size, Long id) {
		Member member = memberService.getMemberById(id);
		PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<Portfolio> postPage = portfolioRepository.findAllByMember(request, member);
		return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
	}

	public Portfolio getPofoById(Long pofoId) {
		Portfolio portfolio = portfolioRepository.findPortfolioById(pofoId);
		return portfolio;
	}

	public List<Portfolio> getPofoByMemberId(Long memberId) {
		List<Portfolio> portfolios = portfolioRepository.getPofosByMemberId(memberId);
		return portfolios;
	}
}
