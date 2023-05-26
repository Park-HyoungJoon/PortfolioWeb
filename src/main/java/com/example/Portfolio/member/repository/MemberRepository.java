package com.example.Portfolio.member.repository;

import com.example.Portfolio.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	//email 존재 여부 확인!
	@Query(value="select * from member where member_email=?1",nativeQuery = true)
	public Optional<Member> vaildateEmail(String email);
	
	Member findByMemberEmail(String email);
	@Query(value="select member_id from member where member_email=?1",nativeQuery = true)
	public Long findIdByMemberEmail(String email);
	
	@Query(value="select * from Member where member_id=?1",nativeQuery = true)
	Member findMemberById(Long id);
}
