package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.quizapp.enums.Severity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 
@Entity
@Table(name = "quizzes")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(name = "quiz_pic", columnDefinition = "TEXT")
	private String quizPic;

	@Column(nullable = false)
	private Long timeLimit;

	private Boolean randomizeQuestions;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Severity severity;

	@ManyToOne()
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnoreProperties("quizzes")
	private Category category;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Question> questions;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Result> results;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Bookmark> bookmarks;

	@ManyToOne()
	@JoinColumn(name = "creator_id", nullable = false)
	@JsonIgnoreProperties("quizzes")
	private User creator;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public Quiz() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuizPic() {
		return quizPic;
	}

	public void setQuizPic(String quizPic) {
		this.quizPic = quizPic;
	}

	public Long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Boolean getRandomizeQuestions() {
		return randomizeQuestions;
	}

	public void setRandomizeQuestions(Boolean randomizeQuestions) {
		this.randomizeQuestions = randomizeQuestions;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Quiz(Long id, String title, String description, String quizPic, Long timeLimit, Boolean randomizeQuestions,
			Boolean isDeleted, Severity severity, Category category, List<Question> questions, List<Result> results,
			List<Bookmark> bookmarks, User creator, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.quizPic = quizPic;
		this.timeLimit = timeLimit;
		this.randomizeQuestions = randomizeQuestions;
		this.isDeleted = isDeleted;
		this.severity = severity;
		this.category = category;
		this.questions = questions;
		this.results = results;
		this.bookmarks = bookmarks;
		this.creator = creator;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
