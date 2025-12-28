package com.resumeai.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String  id;
	
	@Column(name="filename", nullable= false)
	private String filename;
	//
	@Column(name="file_path", nullable = false)
	private String filePath;
	//file size in bytes 
	@Column(name="file_size")
	private Long fileSize;
	//document type pdf,docx ,etc
	@Column(name="document_type")
	@Builder.Default
	private String documentType ="GENERAL"; // resume,jd,report,general
	
	//processing Status
	@Column(name="is_processed", nullable= false)
	@Builder.Default
	private boolean isProcessed = false;
	
	//chunk count
	@Column(name= "chunk_count")
	@Builder.Default
	private Integer chunkCount=0;
	
	//processing metrics
	@Column(name= "processing_time")
	private Long processingTime;
	
	@Column(name="word_count")
	private Integer wordCount;
	
	//metadata
	@Column(name= "upload_date", nullable =false)
	private LocalDateTime uploadDate;
	
	@Column(name="last_accessed")
	private LocalDateTime lastAccessed;
	
	// many docs can belong to one user 
	//Foreign key to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable =false)
	private User user;
	
	// one to many -one doc can have multiple chunks
	@OneToMany(mappedBy="document",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@Builder.Default
	private List<DocumentChunk> chunks= new ArrayList<>();
	
	// one to many -one doc can have multiple chat sessions
	@OneToMany(mappedBy="document",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@Builder.Default
	private List <ChatSession> chatSessions =new ArrayList<>();
	
	@PrePersist
	protected void onCreate() {
		uploadDate= LocalDateTime.now();
		lastAccessed= LocalDateTime.now();
	}
	@PreUpdate
	protected void onUpdate() {
		lastAccessed= LocalDateTime.now();
	}
	
	// helpers 
	public String getFileSizeFormatted() {
		if(fileSize==null)return "0 Bytes";
		if(fileSize<1024)return fileSize+"Bytes";
		int exp=(int)(Math.log(fileSize)/Math.log(1024));
		String pre ="KMGTPE".charAt(exp-1)+"B";
		return String.format("%.1f %s", fileSize/Math.pow(1024, exp),pre);
	}
	
	public boolean isResume() {
		return "RESUME".equals(documentType);
	
	}
	
	public boolean isJobDescription() {
		return "JOB_DESCRIPTION".equals(documentType);
	}
}
