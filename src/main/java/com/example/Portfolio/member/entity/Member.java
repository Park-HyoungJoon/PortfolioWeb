package com.example.Portfolio.member.entity;

import com.example.Portfolio.member.dto.MemberDto;
import com.example.Portfolio.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;
    
    @Column(unique = true , length = 255)
	private String memberEmail;

    //비밀번호
    @Column(length = 255 )
    private String memberPassword;
    
    //닉네임(Unique)`
    @Column(length = 10)
	private String memberNickname;
    

	@NotEmpty(message = "이름은 필수 입력 값입니다.")
	private String name;
	

	private String memberImageRoot;
	private String school;
	private String schoolSection;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemberEmail(memberDto.getEmail());
        member.setMemberNickname(memberDto.getNickname());
        String password = passwordEncoder.encode(memberDto.getPassword());
        member.setMemberPassword(password);
        member.setName(memberDto.getName());
        member.setRole(Role.USER);
        return member;
    }

}
