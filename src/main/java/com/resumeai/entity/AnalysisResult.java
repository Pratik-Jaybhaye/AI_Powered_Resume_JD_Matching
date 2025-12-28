package com.resumeai.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="analysis_result",indexes= {
		@Index(name="idx_analysis_category",columnList = "analysis_id,  category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisResult {
	
	@Id
	@GeneratedValue(strategy =GenerationType.UUID)
	private String id;
	
	// result category
	@Column(name="category",nullable=false)
	private String category;
	
	//score 
	@Column(name="score")
	private Double score;
	
	// detailing 
	@Column(name= "findings",columnDefinition="JSON")
	private String findings;
	
	// recommendations
	@Column(name="recommendations",columnDefinition="JSON")
	private String recommendations;
	
	@Column(name="created_at",nullable=false)
	private LocalDateTime createdAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="analysis_id",nullable =false)
	private ResumeAnalysis resumeAnalysis;
	
	@PrePersist
	protected void onCreate() {
		createdAt=LocalDateTime.now();
		
	}

}
