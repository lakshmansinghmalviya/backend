package com.example.quizapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(nullable = false)
	private boolean logout;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(name = "profile_pic")
	private String profilePic;

	@Column(nullable = false)
	private String role;

	@Column(nullable = true)
	private String token;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private String bio;
	private boolean isActive;

	@OneToMany(mappedBy = "creator")
	private List<Category> categories;

	@OneToMany(mappedBy = "creator")
	private List<Quiz> quizzes;

	@OneToMany(mappedBy = "user")
	private List<QuizAttempt> quizzesAttempted;

	@OneToMany(mappedBy = "user")
	private List<Feedback> feedbacks;

	@OneToMany(mappedBy = "user")
	private List<Bookmark> bookmarks;

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
