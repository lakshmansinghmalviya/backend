package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

@Entity
@Table(name = "questions")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(nullable = false, unique = true)
	private String text;

	@Column(name = "question_type", nullable = false)
	private String questionType;

	@Column(name = "randomize_options")
	private Boolean randomizeOptions;

	@Column(name = "question_pic", columnDefinition = "TEXT")
	private String questionPic;

	@Column(name = "maxscore", nullable = false)
	private Long maxScore;

	@ManyToOne()
	@JoinColumn(name = "quiz_id", nullable = false)
	@JsonIgnoreProperties({ "categories", "quizzes", "results", "feedbacks", "bookmarks", "questions" })
	private Quiz quiz;

	@ManyToOne()
	@JoinColumn(name = "creator_id", nullable = false)
	@JsonBackReference
	private User creator;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Option> options;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Feedback> feedbacks;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Boolean getRandomizeOptions() {
		return randomizeOptions;
	}

	public void setRandomizeOptions(Boolean randomizeOptions) {
		this.randomizeOptions = randomizeOptions;
	}

	public String getQuestionPic() {
		return questionPic;
	}

	public void setQuestionPic(String questionPic) {
		this.questionPic = questionPic;
	}

	public Long getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
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

	public Question() {
		super();
	}

	public Question(Long id, Boolean isDeleted, String text, String questionType, Boolean randomizeOptions,
			String questionPic, Long maxScore, Quiz quiz, User creator, List<Option> options, List<Feedback> feedbacks,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.isDeleted = isDeleted;
		this.text = text;
		this.questionType = questionType;
		this.randomizeOptions = randomizeOptions;
		this.questionPic = questionPic;
		this.maxScore = maxScore;
		this.quiz = quiz;
		this.creator = creator;
		this.options = options;
		this.feedbacks = feedbacks;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
