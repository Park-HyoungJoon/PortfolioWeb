package com.example.Portfolio.pofo.repository;

import com.example.Portfolio.pofo.entity.PortfolioCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioCareerRepository extends JpaRepository<PortfolioCareer,Long>{

	@Query(value="SELECT a.item from portfolio_career a where a.portfolio_id=:pofoId and a.item  LIKE :part%",nativeQuery = true)
	List<String> findItemsByPart(@Param("pofoId") Long pofoId, @Param("part") String part);

	@Query(value="SELECT * from portfolio_career  where portfolio_id=?1",nativeQuery = true)
	List<PortfolioCareer> getPortfolioCareerByPortfolio(Long long1);

}
