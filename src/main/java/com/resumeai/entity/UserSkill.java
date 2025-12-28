package com.resumeai.entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name="user_skills",uniqueConstraints= {
		@UniqueConstraint(columnNames= {
				"user_id","skill_id"
		})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkill {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	// proficiency level 
	@Column(name="proficiency_level",nullable=false)
	@Builder.Default
	private Integer proficiencyLevel=1;
	
	@Column(name="experience_months")
	private Integer experienceMonths;
	@Column(name="last_used")
	private java.time.LocalDate lastUsed;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="skill_id",nullable =false)
	private Skill skill;
	
	  public String getProficiencyLabel() {
	        switch (proficiencyLevel) {
	            case 1: return "Beginner";
	            case 2: return "Novice";
	            case 3: return "Intermediate";
	            case 4: return "Advanced";
	            case 5: return "Expert";
	            default: return "Unknown";
	        }
	    }
	  public boolean isRecentlyUsed() {
	        if (lastUsed == null) return false;
	        return lastUsed.isAfter(java.time.LocalDate.now().minusMonths(6));
	    }
	
}
