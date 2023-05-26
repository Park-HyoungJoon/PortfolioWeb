package com.example.Portfolio.pofo.service;

import com.example.Portfolio.pofo.entity.Portfolio;
import com.example.Portfolio.pofo.entity.PortfolioCareer;
import com.example.Portfolio.pofo.repository.PortfolioCareerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PortfolioCareerService {
	@Autowired
	PortfolioCareerRepository portfolioCareerRepository;
	@Autowired
	PortfolioService portfolioService;
	
	
	public List<String> getItems(Long pofoId, String part) {
		List<String> item = portfolioCareerRepository.findItemsByPart(pofoId,part);
		List<String> resetItem = new ArrayList<>();
		for (String iteme : item) {
			String removePart = iteme.replaceAll(part+"_","");
			resetItem.add(removePart);
		}
		return resetItem;
	}


	public List<PortfolioCareer> getPortfolioCareerByPortfolio(Portfolio portfolio) {
		List<PortfolioCareer> portfolioCareer = portfolioCareerRepository.getPortfolioCareerByPortfolio(portfolio.getId());
		return portfolioCareer;
	}

}
