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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCategoryPic() {
		return categoryPic;
	}

	public void setCategoryPic(String categoryPic) {
		this.categoryPic = categoryPic;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public Category() {
		super();
	}

	public Category(Long id, String name, String description, Boolean isDeleted, LocalDateTime createdAt,
			String categoryPic, LocalDateTime updatedAt, User creator, List<Quiz> quizzes) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.categoryPic = categoryPic;
		this.updatedAt = updatedAt;
		this.creator = creator;
		this.quizzes = quizzes;
	}
}
