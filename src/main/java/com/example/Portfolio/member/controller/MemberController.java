package com.example.Portfolio.member.controller;

import com.example.Portfolio.member.dto.MemberDto;
import com.example.Portfolio.member.dto.ValidateMemberDto;
import com.example.Portfolio.member.entity.Member;
import com.example.Portfolio.member.repository.MemberRepository;
import com.example.Portfolio.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class MemberController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("join")
	public String main( MemberDto memberDto,Model model) {
		return "member/join";
	}
	

	@PostMapping("member/join")
	public String registerMember(MemberDto memberDto,Model model) {
		Member member = new Member().createMember(memberDto, passwordEncoder);
		memberService.save(member);
		
		return "main";
	}
	@PostMapping(value="login")
	public String loginMember() {
		return "main";
	}
	
	//이메일 존재여부 확인 컨트롤러
	@RequestMapping(value = "validate/email", method = { RequestMethod.POST })
	@ResponseBody // 자바 객체를 HTTP 응답 본문의 객체로 변환
	public Object validateEmail(ValidateMemberDto member, HttpServletResponse rs, Model model) throws IOException {
		String email = member.getEmail();
		Optional<Member> memberVal = memberRepository.vaildateEmail(email);
		Map<String, String> map = new HashMap<String,String>(); 
		
		if(memberVal.isPresent()) {
	        map.put("data","1");
			return map;
		}
		else {
			map.put("data", "0");
			return map;
		}
		
	}

}
