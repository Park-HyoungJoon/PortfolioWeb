package com.example.Portfolio.pofo.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="project_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "portfolio_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Portfolio portfolio;
	@Lob
	private String projectComment;
	private String projectImageRoot;
	
}
