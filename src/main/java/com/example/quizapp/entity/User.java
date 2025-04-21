package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.quizapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_approved")
	private Boolean isApproved;

	private boolean isLogout;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(name = "profile_pic", columnDefinition = "TEXT")
	private String profilePic;

	@Column(name = "education")
	private String education;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	private String bio;

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Category> categories;

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Quiz> quizzes;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Result> results;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Feedback> feedbacks;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Bookmark> bookmarks;

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Question> questions;
}
