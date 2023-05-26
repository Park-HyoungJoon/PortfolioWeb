package com.example.Portfolio.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {

	@NotEmpty(message = "이메일은 필수 입력 값입니다.")
	private String email;

	@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	private String password;

	@NotEmpty(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;
	@NotEmpty(message = "이름은 필수 입력 값입니다.")
	private String name;
	

	private String memberImageRoot;
	private String school;
	private String schoolSection;

}
