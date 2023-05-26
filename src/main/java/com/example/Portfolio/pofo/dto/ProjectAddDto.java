package com.example.Portfolio.pofo.dto;


import com.example.Portfolio.pofo.entity.Project;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Slf4j
public class ProjectAddDto {

	private Long id;
	private Long realPofoId;
	private String projectComment;
	private Project project;
	private String projectImageRoot;
	private MultipartFile ProjectImage;

    @JsonProperty("projectInfoHidden")
    String projectInfoHidden;


    @JsonProperty("pofoId")
    Long pofoId;
    
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Project createProject() {
		return modelMapper.map(this, Project.class);
	}
	
	public static ProjectAddDto of(Project project) {
		return modelMapper.map(project, ProjectAddDto.class);
	}
	
	
}
