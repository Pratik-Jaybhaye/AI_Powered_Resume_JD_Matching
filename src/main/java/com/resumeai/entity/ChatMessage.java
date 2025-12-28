package com.resumeai.entity;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Entity
@Table(name="chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	@Id 
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	//message content 
	@Column(name="content", nullable=false, columnDefinition="TEXT")
	private String content;
	
	//Sender type user or AI
	@Column(name="sender_type", nullable= false)
	private String senderType;
	
	@Column(name ="message_role")
	private String messageRole;
	
	@Column(name= "model_used")
	private String modelUsed;
	
	@Column(name="token_count")
	private Integer tokenCount;
	
	@Column(name="confidence_score")
	private Double confidenceScore;
	
	@Column(name="sources",columnDefinition="JSON")
	private String sources;
	
	@Column(name="metadata",columnDefinition="JSON")
	private String metadata;
	
	@Column(name="timestamp" ,nullable =false)
	private LocalDateTime timestamp;
	
	// foreign keys to chatsession 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="session_id",nullable=false)
	private ChatSession chatSession;
	
	@PrePersist
	protected void onCreate() {
		timestamp =LocalDateTime.now();
	}
	
	//helper methods 
	public boolean isUserMessage() {
		return "USER".equals(senderType);
	}
	
	public boolean isAiMessage() {
		return "AI".equals(senderType);
	}
	
	public Map<String, Object>getSourcesAsMap(){
		try {
			return new com.fasterxml.jackson.databind.ObjectMapper().readValue(sources, Map.class);
		} catch(Exception e) {
			return Map.of();
		}
	}
	
	// factory methods
	public static ChatMessage createUserMessage(String content ,ChatSession session) {
		return ChatMessage.builder().content(content).senderType("USER").messageRole("user").chatSession(session).build();
	}
	 public static ChatMessage createAiMessage(String content, ChatSession session, 
             String model, Integer tokens, Double confidence) {
return ChatMessage.builder()
.content(content)
.senderType("AI")
.messageRole("assistant")
.modelUsed(model)
.tokenCount(tokens)
.confidenceScore(confidence)
.chatSession(session)
.build();
}

}
