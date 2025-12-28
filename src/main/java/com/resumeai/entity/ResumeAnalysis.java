package com.resumeai.entity;
import lombok.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name="resume_analyses",indexes= {
		@Index(name="idx_user_date",columnList="user_id,analysis_date"),
		@Index(name="idx_job_title",columnList ="job_title")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeAnalysis {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	
	@Column(name="resume_text",nullable =false, columnDefinition ="LONGTEXT")
	private String resumeText;
	
	@Column(name="jd_text",nullable=false ,columnDefinition="LONGTEXT")
	private String jdText;
	
	@Column(name="company")
	private String company;
	
	@Column(name="match_score",nullable =false)
	private Double matchScore;
	
	@Column(name="category_scores", columnDefinition  = "JSON")
	private String categoryScores;
	
	@Column(name="strengths",columnDefinition="JSON")
	private String strengths; // json array
	
	@Column(name="suggestions" , columnDefinition="JSON")
	private String suggestions;
	
	//extracted entities 
	@Column(name="extracted_skills", columnDefinition="JSON")
	private String extractedSkills;
	
	@Column(name="missing_keywords",columnDefinition = "JSON")
	private String missingKeywords;
	
	@Column(name="analysis_date",nullable =false)
	private LocalDateTime analysisDate;
	
	@Column(name="analysis_version")
	private String analysisVersion;
	
	@Column(name="processing_time")
	private Long processingTime; // in milliseconds 
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable= false)
	private User user;
	
	@PrePersist
	protected void onCreate() {
		analysisDate =LocalDateTime.now();
		if(analysisVersion==null) {
			analysisVersion ="v1.0";
		}
	}
	
	public String getScoreCategory() {
		if(matchScore>=90 )return "Excellent";
		if(matchScore>=80 )return "Very Good";
		if(matchScore>=70 )return "Good";
		if(matchScore>=60 )return "Fair";
		return "Needs Improvement";
	}
	  public Map<String, Double> getCategoryScoresAsMap() {
	        try {
	            return new com.fasterxml.jackson.databind.ObjectMapper()
	                .readValue(categoryScores, Map.class);
	        } catch (Exception e) {
	            return Map.of();
	        }
	    }
	  public String getResumePreview(int maxLength) {
	        if (resumeText == null) return "";
	        if (resumeText.length() <= maxLength) return resumeText;
	        return resumeText.substring(0, maxLength) + "...";
	    }
	  public String getJdPreview(int maxLength) {
	        if (jdText == null) return "";
	        if (jdText.length() <= maxLength) return jdText;
	        return jdText.substring(0, maxLength) + "...";
	    }
	  public static ResumeAnalysisBuilder builder() {
	        return new CustomResumeAnalysisBuilder();
	    }
	  private static class CustomResumeAnalysisBuilder extends ResumeAnalysisBuilder {
	        @Override
	        public ResumeAnalysis build() {
	            ResumeAnalysis analysis = super.build();
	            if (analysis.getMatchScore() == null) {
	                analysis.setMatchScore(0.0);
	            }
	            return analysis;
	        }
	    }
	
	
}
