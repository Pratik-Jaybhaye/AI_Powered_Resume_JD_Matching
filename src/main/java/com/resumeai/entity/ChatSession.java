package com.resumeai.entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="chat_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSession {

	@Id 
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	// session name user defined or auto generated
	@Column(name="session_name", nullable= false)
	private String sessionName;
	
	//session type 
	@Column(name="session_type")
	@Builder.Default
	private String sessionType= "DOCUMENT_QA";
	
	@Column(name="message_count")
	@Builder.Default
	private Integer messageCount=0;
	
	@Column(name="token_usage")
	@Builder.Default
	private Long tokenUsage=0L;
	
	// timestamps 
	@Column(name="created_date",nullable=false)
	private LocalDateTime createdDate;
	
	@Column(name="last_activity", nullable=false)
	private LocalDateTime lastActivity;
	
	// Foreign keys
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="user_id",nullable = false)
	private User user;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="document_id")
	private Document document;
	
	//one to many -one session has many messages
	
	@OneToMany(mappedBy="chatSession",cascade=CascadeType.ALL, fetch= FetchType.LAZY)
	@Builder.Default
	private List<ChatMessage>messages= new ArrayList<>();
	
	@PrePersist
	protected void onCreate() {
		createdDate =LocalDateTime.now();
		lastActivity=LocalDateTime.now();
		if(sessionName==null || sessionName.trim().isEmpty()) {
			sessionName="chat Session"+createdDate.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd,HH:mm"));
		}
	}
	
	@PreUpdate
	protected void onUpdate() {
		lastActivity = LocalDateTime.now();
		
	}
	
	public void addMessage(ChatMessage message) {
		messages.add(message);
		message.setChatSession(this);
		messageCount = messages.size();
		if(message.getTokenCount() != null) {
			tokenUsage+=message.getTokenCount();
		}
		
	}
	
	public void removeMessage(ChatMessage message) {
		messages.remove(message);
		message.setChatSession(null);
		messageCount= messages.size();
		if(message.getTokenCount() !=null) {
			tokenUsage= Math.max(0, tokenUsage-message.getTokenCount());
		}
	}
	public ChatMessage getLastMessage() {
		if(messages.isEmpty()) {
			return null;
		}
		return messages.get(messages.size()-1);
	}
	public boolean isDocumentSession() {
		return document !=null;
	}
	
	
}
