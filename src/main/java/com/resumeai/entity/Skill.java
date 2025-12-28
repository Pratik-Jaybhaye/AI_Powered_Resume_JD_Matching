package com.resumeai.entity;
import lombok.*;
import jakarta.persistence.*;
import java.time.*;
import java.util.HashSet;
import java.util.Set;
@Entity 
@Table(name="skills", indexes= {
		@Index(name="idx_skill_name",columnList="name"),
		@Index(name="idx_category",columnList="category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// skill info 
	@Column(name="name",nullable=false,unique=true)
	private String name;
	@Column(name="category",nullable=false)
	private String category;
	@Column(name="description", columnDefinition ="TEXT")
	private String description;
	
	@Column(name="popularity_score")
	private Double popularityScore;
	
	@Column(name="created_at",nullable =false)
	private LocalDateTime createdAt;
	
	@Column(name="last_updated")
	private LocalDateTime lastUpdated;
	
	@OneToMany(mappedBy="skill",cascade=CascadeType.ALL,fetch =FetchType.LAZY)
	@Builder.Default
	private Set<UserSkill> userSkills = new HashSet<>();
	
	@PrePersist
	protected void onCreate() {
		createdAt=LocalDateTime.now();
		lastUpdated = LocalDateTime.now();
		if(popularityScore==null) {
			popularityScore=0.0;
		}
	}
	
	@PreUpdate
	protected void onUpdate() {
		lastUpdated= LocalDateTime.now();
		
	}
	
	
}
