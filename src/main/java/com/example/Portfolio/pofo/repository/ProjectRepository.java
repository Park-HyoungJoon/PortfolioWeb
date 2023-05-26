package com.example.Portfolio.pofo.repository;

import com.example.Portfolio.pofo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	@Query(value = "select id from project where portfolio_id = ?1",nativeQuery = true)
	List<Long> getIdsByPortfolio(Long id);

	@Query(value = "select MIN(project_id) from project where portfolio_id = ?1",nativeQuery=true)
	Long getfirstByPofoId(Long id);

	@Query(value = "select * from project where project_id = ?1",nativeQuery =true)
	Project getPofoById(Long firstProject_id);

	List<Project> findAllByPortfolioId(Long id);

}
