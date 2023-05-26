package com.example.Portfolio.pofo.service;

import com.example.Portfolio.pofo.dto.ProjectAddDto;
import com.example.Portfolio.pofo.entity.Portfolio;
import com.example.Portfolio.pofo.entity.Project;
import com.example.Portfolio.pofo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	PortfolioService portfolioService;
	
	public List<Long> getIdsByPortfolio(Portfolio portfolio) {
		List<Long> Ids = projectRepository.getIdsByPortfolio(portfolio.getId());

		return Ids;
	}

	public Project save(Project project) {
		Project project2 = projectRepository.save(project);
		return project2;
		
	}

	public Project getProjectFirstByPortfolio(Portfolio portfolio2) {
		Long firstProject_id = projectRepository.getfirstByPofoId(portfolio2.getId());
		Project project = projectRepository.getPofoById(firstProject_id);
		return project;
	}

	public Project saveProject(ProjectAddDto projectAddDto, Long pofoId) {
		Project project = projectAddDto.createProject();
		Portfolio portfolio = portfolioService.getPofoById(pofoId);
		project.setPortfolio(portfolio);
		projectRepository.save(project);
		return project;
	}

	public List<Project> getProjectByPortfolio(Portfolio pofo) {
		List<Project> projects = projectRepository.findAllByPortfolioId(pofo.getId());
		return projects;
	}

}
