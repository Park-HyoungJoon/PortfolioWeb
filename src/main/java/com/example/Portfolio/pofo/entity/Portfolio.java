package com.example.Portfolio.pofo.entity;

import com.example.Portfolio.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="portfolio_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	private String pofoTitle;
	@Lob
	private String comment;
	
}
	