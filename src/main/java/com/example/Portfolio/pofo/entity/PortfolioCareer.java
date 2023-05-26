package com.example.Portfolio.pofo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PortfolioCareer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "protfolio_career_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="portfolio_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Portfolio portfolio;
	
	private String item;
	
	public void updatePofoCareer(String item) {
		this.item = item;
	}
}
