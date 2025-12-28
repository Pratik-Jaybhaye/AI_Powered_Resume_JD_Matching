package com.resumeai.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="project_suggestions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectSuggestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	
	 @Column(name = "title", nullable = false)
	    private String title;
	    
	    @Column(name = "description", columnDefinition = "TEXT")
	    private String description;
	    
	    @Column(name = "difficulty_level")
	    private String difficultyLevel;
	
	    @Column(name = "estimated_hours")
	    private Integer estimatedHours;
	
	    @Column(name="skills_covered", columnDefinition="JSON")
	    private String skillsCovered;
	    
	    @Column(name = "resources", columnDefinition = "JSON")
	    private String resources;
	    
	    @Column(name = "generated_at", nullable = false)
	    private LocalDateTime generatedAt;
	    
	    
	    @Column(name = "is_completed")
	    @Builder.Default
	    private Boolean isCompleted = false;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "analysis_id")
	    private ResumeAnalysis resumeAnalysis;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
	    
	    @PrePersist
	    protected void onCreate() {
	        generatedAt = LocalDateTime.now();
	    }
	    
}
