package com.example.Portfolio.main;

import com.example.Portfolio.pofo.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@Autowired
	private PortfolioService portfolioService;
	
	@GetMapping("/")
	public String main(
            Model model) {
		return "main";
	}
}
