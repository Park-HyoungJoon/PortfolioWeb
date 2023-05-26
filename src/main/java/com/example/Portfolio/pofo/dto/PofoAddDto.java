package com.example.Portfolio.pofo.dto;

import com.example.Portfolio.member.entity.Member;
import com.example.Portfolio.pofo.entity.Portfolio;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Slf4j
public class PofoAddDto {
	private Long id;
	@NotBlank(message = "이름은 필수 입력입니다.")
	String name;
	private String school;
	private String schoolSection;
	private String comment;
	private Member member;
	private String memberImageRoot;
	private String pofoTitle;
	private MultipartFile memberImage;

    @JsonProperty("lectureInfoHidden")
    String lectureInfoHidden;

    @JsonProperty("existPofo")
    Long existPofo;
    
	private List<String> license = new ArrayList<>();
	private List<String> career = new ArrayList<>();
	private List<String> skill = new ArrayList<>();

	private static ModelMapper modelMapper = new ModelMapper();
	
	public Portfolio createPortfolio() {
		return modelMapper.map(this, Portfolio.class);
	}
	
	public static PofoAddDto of(Portfolio portfolio) {
		return modelMapper.map(portfolio, PofoAddDto.class);
	}
	
	public Member pofoToMember(PofoAddDto pofoAddDto,Member member) {
		member.setMemberImageRoot(pofoAddDto.getMemberImageRoot());
		member.setName(pofoAddDto.getName());
		member.setSchool(pofoAddDto.getSchool());
		member.setSchoolSection(pofoAddDto.getSchoolSection());
		return member;
	}
	
	public List<String> itemToCareer(List<String> licenses, List<String> careers, List<String> skills) {
		List<String> items = new ArrayList<>();
		if (licenses != null || !licenses.isEmpty()) {
			for (String license : licenses) {
				items.add("license_"+license);
			}
		}
		if (careers != null || !careers.isEmpty()) {
			for (String career : careers) {
				items.add("career_"+career);
			}
		}
		if (skills != null || !skills.isEmpty()) {
			for (String skill : skills) {
				items.add("skill_"+skill);
			}
		}
		log.info(licenses.get(0));
		return items;
	}
}
