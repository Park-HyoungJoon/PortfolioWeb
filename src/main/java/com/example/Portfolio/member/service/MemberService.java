package com.example.Portfolio.member.service;

import com.example.Portfolio.member.entity.Member;
import com.example.Portfolio.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	public void save(Member member) {
		Member member2 = memberRepository.save(member);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			Member member = memberRepository.findByMemberEmail(email);
			
			if(member ==null) {
				throw new UsernameNotFoundException(email);
			}
			return User.builder()
					.username(member.getMemberEmail())
					.password(member.getMemberPassword())
					.roles(member.getRole().toString())
					.build();
	}

	public Long getIdByEmail(String email) {
		Long id = memberRepository.findIdByMemberEmail(email);
		
		return id;
	}

	public Member getMemberById(Long id) {
		Member member = memberRepository.findById(id).get();
		// TODO Auto-generated method stub
		return member;
	}
}
