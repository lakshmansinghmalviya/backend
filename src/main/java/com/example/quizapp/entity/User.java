package com.example.quizapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.quizapp.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_approved")
	private Boolean isApproved;

	@Column()
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

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	private String bio;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Category> categories;

	@OneToMany(mappedBy = "creator")
	@JsonManagedReference
	private List<Quiz> quizzes;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Result> results;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Feedback> feedbacks;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Bookmark> bookmarks;

	@OneToMany(mappedBy = "creator")
	@JsonBackReference
	private List<Question> questions;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public User(Long id, String email, LocalDateTime lastLogin, Boolean isDeleted, Boolean isActive, Boolean isApproved,
			boolean isLogout, String name, String password, String profilePic, String education, Role role,
			LocalDateTime createdAt, LocalDateTime updatedAt, String bio, List<Category> categories, List<Quiz> quizzes,
			List<Result> results, List<Feedback> feedbacks, List<Bookmark> bookmarks, List<Question> questions) {
		super();
		this.id = id;
		this.email = email;
		this.lastLogin = lastLogin;
		this.isDeleted = isDeleted;
		this.isActive = isActive;
		this.isApproved = isApproved;
		this.isLogout = isLogout;
		this.name = name;
		this.password = password;
		this.profilePic = profilePic;
		this.education = education;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.bio = bio;
		this.categories = categories;
		this.quizzes = quizzes;
		this.results = results;
		this.feedbacks = feedbacks;
		this.bookmarks = bookmarks;
		this.questions = questions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isLogout() {
		return isLogout;
	}

	public void setLogout(boolean isLogout) {
		this.isLogout = isLogout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public List<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(List<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public User() {
		super();
	}

}
