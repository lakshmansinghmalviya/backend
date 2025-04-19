package com.example.quizapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "created_at", updatable = false)
	protected LocalDateTime createdAt;

	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@Column(name = "is_deleted")
	protected Boolean isDeleted = false;

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
