package com.resumeai.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.cglib.core.Local;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 @Column(unique = true, nullable = false)
	 private String email;
	 
	 @Column(nullable = false)
	 private String password;

	 
	    @Column(name ="first_name", nullable = false)
	    private String firstName;
	    
	   @Column(name="last_name")
	   private String lastName;
	   
	   @Column(name="subscription_type",nullable=false)
	   @Builder.Default
	   private String subscriptionType="FREE";
	   
	   @Column(name="created_at",nullable=false)
	   private LocalDateTime createdAt;
	   
	   @Column(name="last_login")
	   private LocalDateTime lastLogin;
	   
	   @Column(name="updated_at")
	   private LocalDateTime updatedAt;
	   
	   //for account status active or not used flag
	   @Column(name="is_active",nullable =false)
	   @Builder.Default
	   private boolean isActive =true;
	   
	   //Relationships 
	   @OneToMany(mappedBy="user",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	   @Builder.Default
	   private Set<Document> documents =new HashSet<>();
	   
	   @OneToMany(mappedBy="user",cascade=CascadeType.ALL , fetch =FetchType.LAZY)
	   @Builder.Default
	   private Set<ChatSession> chatSessions = new HashSet<>();
	   
	   @OneToMany(mappedBy ="user", cascade=CascadeType.ALL , fetch = FetchType.LAZY)
	   @Builder.Default
	   private Set<ResumeAnalysis>resumeAnalysis = new HashSet<>();
	   
	 @PrePersist
	 protected void onCreate() {
		 createdAt=LocalDateTime.now();
		 isActive= true;
	 }
	 
	 @PreUpdate
	 protected void onUpdate()
	 {
		 updatedAt= LocalDateTime.now();
		 
	 }
	 
	 // Helper methods 
	 public String getFullName() {
		 return firstName+" "+lastName;
		 
	 }
	 
	 public boolean isPremiumUser() {
		 return "PREMIUM".equals(subscriptionType);
	 }
	   
	   
	   
}
