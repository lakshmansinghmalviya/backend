package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "categories")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "category_pic", columnDefinition = "TEXT")
	private String categoryPic;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne()
	@JoinColumn(name = "creator_id", nullable = false)
	@JsonIgnoreProperties({ "categories", "quizzes", "results", "feedbacks", "bookmarks", "questions" })
	private User creator;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Quiz> quizzes;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
