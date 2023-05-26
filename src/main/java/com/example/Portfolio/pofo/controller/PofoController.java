package com.example.Portfolio.pofo.controller;

import com.example.Portfolio.member.entity.Member;
import com.example.Portfolio.member.repository.MemberRepository;
import com.example.Portfolio.member.service.MemberService;
import com.example.Portfolio.pofo.dto.PofoAddDto;
import com.example.Portfolio.pofo.dto.ProjectAddDto;
import com.example.Portfolio.pofo.entity.Portfolio;
import com.example.Portfolio.pofo.entity.PortfolioCareer;
import com.example.Portfolio.pofo.entity.Project;
import com.example.Portfolio.pofo.repository.PortfolioRepository;
import com.example.Portfolio.pofo.service.PortfolioCareerService;
import com.example.Portfolio.pofo.service.PortfolioService;
import com.example.Portfolio.pofo.service.ProjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/pofo")
@Slf4j
public class PofoController {

    @Value("${part5.upload.path}")
    private String fileRoot;
    
    
	@Autowired
	private PortfolioService portfolioService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PortfolioCareerService portfolioCareerService;

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@GetMapping(value="/add")
	public String pofoAdd(Principal principal, Model model) {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		Member member = memberRepository.findMemberById(memberId);
		
		model.addAttribute("member",member);
		return "portfolio/pofoAdd";
	}
	
	@PostMapping(value="/add", consumes = "multipart/form-data")
	public String pofoAdd(@Valid PofoAddDto pofoAddDto, BindingResult bindingResult, Model model, Principal principal) throws IllegalStateException, IOException {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		if(bindingResult.hasErrors()) {
			return "main";
		}
        String uuid = UUID.randomUUID().toString();
        String uuidFileName = uuid + "_" + pofoAddDto.getMemberImage().getOriginalFilename();
        File saveFile = new File(fileRoot , uuidFileName);
        pofoAddDto.getMemberImage().transferTo(saveFile);
        pofoAddDto.setMemberImageRoot(uuidFileName);
        pofoAddDto.setComment(pofoAddDto.getLectureInfoHidden());
			Portfolio portfolio =  portfolioService.savePofo(pofoAddDto,memberId);
			List<PortfolioCareer> portfolioCareer = portfolioCareerService.getPortfolioCareerByPortfolio(portfolio);
			model.addAttribute("portfolio",portfolio);
			model.addAttribute("portfolioCareer",portfolioCareer);
			try {
			List<Long> projectIds = projectService.getIdsByPortfolio(portfolio);
			model.addAttribute("projectIds",projectIds);

			return "portfolio/pofoAddProject";}
			catch (Exception e) {
				Project project = new Project();
				project.setPortfolio(portfolio);
				Project project2 = projectService.save(project);
				List<Long> projectIds = new ArrayList<>();
				projectIds.add(project2.getId());
				model.addAttribute("projectIds",projectIds);
				return "portfolio/pofoAddProject";
			}
			
	}
	@GetMapping(value = "/mypofo")
	public String myPofo(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "8") int size,
            Model model , Principal principal) {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		List<Portfolio> portfolios = portfolioService.getPofoByMemberId(memberId);
		List<Project> projects = new ArrayList<>();
		for (Portfolio portfolio2 : portfolios) {
			Project firstProject = projectService.getProjectFirstByPortfolio(portfolio2);
				projects.add(firstProject);
		}
		model.addAttribute("partPage",portfolioService.getPage(pageNumber,size,memberId));
		model.addAttribute("projects",projects);
			
		return "portfolio/mypofo";
	}
	@GetMapping(value = "/detail/{id}")
	public String pofoDetail(@PathVariable("id") Long pofoId,
            Model model , Principal principal) {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		Portfolio pofo = portfolioService.getPofoById(pofoId);
		List<String> career = portfolioCareerService.getItems(pofoId,"career");
		List<String> skill = portfolioCareerService.getItems(pofoId,"skill");
		List<String> license = portfolioCareerService.getItems(pofoId,"license");
		List<Project> projects = projectService.getProjectByPortfolio(pofo);
		model.addAttribute("projects",projects);
		model.addAttribute("portfolio",pofo);
		model.addAttribute("careers",career);
		model.addAttribute("skills",skill);
		model.addAttribute("licenses",license);
		return "portfolio/pofoDetail";
	}
	
	@PostMapping(value="/project/add", consumes = "multipart/form-data")
	public String projectAdd(@Valid ProjectAddDto projectAddDto,BindingResult bindingResult, Model model,Principal principal) throws IllegalStateException, IOException {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		if(bindingResult.hasErrors()) {
			return "main";
		}
        String uuid = UUID.randomUUID().toString();
        String uuidFileName = uuid + "_" + projectAddDto.getProjectImage().getOriginalFilename();
        File saveFile = new File(fileRoot , uuidFileName);
        projectAddDto.getProjectImage().transferTo(saveFile);
        projectAddDto.setProjectImageRoot(uuidFileName);
        projectAddDto.setProjectComment(projectAddDto.getProjectInfoHidden());
        Portfolio portfolio = portfolioService.getPofoById(projectAddDto.getPofoId());
			Project project =  projectService.saveProject(projectAddDto,projectAddDto.getPofoId());
			List<PortfolioCareer> portfolioCareer = portfolioCareerService.getPortfolioCareerByPortfolio(portfolio);
			model.addAttribute("portfolio",portfolio);
			model.addAttribute("portfolioCareer",portfolioCareer);
			try {
			List<Long> projectIds = projectService.getIdsByPortfolio(portfolio);
			model.addAttribute("projectIds",projectIds);

			return "portfolio/pofoAddProject";}
			catch (Exception e) {
				Project project1 = new Project();
				project1.setPortfolio(portfolio);
				Project project2 = projectService.save(project1);
				List<Long> projectIds = new ArrayList<>();
				projectIds.add(project2.getId());
				model.addAttribute("projectIds",projectIds);
				return "portfolio/pofoAddProject";
			}
			
	}

	@GetMapping(value="/remove/{id}/{pageNumber}")
	public String pofoRemove(  @PathVariable(value = "id",required = false) Long id,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "12") int size,
            Model model,Principal principal) throws IllegalStateException, IOException {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		Portfolio portRemove = portfolioService.getPofoById(id);
		portfolioRepository.deletePortfolioById(id);
		List<Portfolio> portfolios = portfolioService.getPofoByMemberId(memberId);
		List<Project> projects = new ArrayList<>();
		for (Portfolio portfolio2 : portfolios) {
			Project firstProject = projectService.getProjectFirstByPortfolio(portfolio2);
				projects.add(firstProject);
		}
		model.addAttribute("partPage",portfolioService.getPage(pageNumber,size,memberId));
		model.addAttribute("projects",projects);
				return "portfolio/mypofo";
			
	}
	@GetMapping(value="/edit/{id}")
	public String pofoEdit(@PathVariable(value = "id",required = false) Long id,
            Model model,Principal principal) throws IllegalStateException, IOException {
		String email = principal.getName();
		Long memberId = memberService.getIdByEmail(email);
		Member member = memberRepository.findMemberById(memberId);
		model.addAttribute("ExistPofo",id);
		Portfolio pofo = portfolioService.getPofoById(id);
		model.addAttribute("portfolio",pofo);
		model.addAttribute("member",member);
		return "portfolio/pofoEdit";
	}
	
}
