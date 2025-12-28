package com.resumeai.entity;

import lombok.*;

import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Arrays;

@Entity
@Table(name="document_chunks",indexes = {
		@Index(name="idx_document_id",columnList="document_id"),
		@Index(name ="idx_chunk_index",columnList="chunk_index")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChunk {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	
	@Column(name="chunk_text",nullable=false,columnDefinition ="TEXT")
	private String chunkText;
	
	@Column(name="chunk_index",nullable=false)
	private Integer chunkIndex;
	
	@Column(name="embedding",columnDefinition ="MEDIUMLOB")
	private byte[] embedding;
	
	@Column(name="vector_id")
	private String vectorId;
	
	//metadata
	@Column(name="metadata",columnDefinition="JSON")
	private String metadata; // JSON format 
	
	@Column(name="word_count")
	private Integer wordCount;
	
	@Column(name="token_count")
	private Integer tokenCount;
	
	// foreign key to Doc - many chunks belongs to one doc
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="document_id",nullable= false)
	private Document document;
	
	//helper methods
	public int calculateWordCount() {
		if(chunkText==null || chunkText.trim().isEmpty()) {
			return 0;
		}
		return chunkText.trim().split("\\s+").length;
	}
	public int calculateTokenCount() {
		if(chunkText==null)return 0;
		return (int)Math.ceil(chunkText.length()/4.0);
	}
	@PrePersist 
	@PreUpdate
	protected void calculateCounts() {
		this.wordCount=calculateWordCount();
		this.tokenCount= calculateTokenCount();
	}
	
	@Override
	public String toString() {
		return "DocumentChunk{"+"id='"+id+'\''+",chunkIndex="+chunkIndex+
				",wordCount="+wordCount+
				",vectorId='"+vectorId+'\''+
				'}';
	}
	
}
