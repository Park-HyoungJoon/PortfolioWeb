package com.example.Portfolio.pofo.repository;

import com.example.Portfolio.member.entity.Member;
import com.example.Portfolio.pofo.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long>{
    Page<Portfolio> findAll(Pageable request);
    Page<Portfolio> findAllByMember(Pageable request,Member member);
    

	@Query(value="select * from portfolio where portfolio_id=?1",nativeQuery = true)
	Portfolio findPortfolioById(Long id);
	
	@Query(value = "select * from portfolio where member_id=?1",nativeQuery = true)
	List<Portfolio> getPofosByMemberId(Long memberId);

    @Modifying
    @Transactional
	void deletePortfolioById(Long id);
}
