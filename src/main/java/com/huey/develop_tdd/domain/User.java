package com.huey.develop_tdd.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    @CreationTimestamp  // 엔티티가 처음 저장될 때 현재 utc 시간으로 자동 할당
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp    // 엔티티가 수정될 때마다 현재 utc 시간으로 자동 업데이트
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected User() {
    }

    public User(String userId, String password, String email) {
        if ((userId == null || userId.isBlank())
        || (password == null || password.isBlank())
        || (email == null || email.isBlank())) {
            throw new IllegalArgumentException();
        }
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public User(String userId, String password, String email, String name) {
        if ((userId == null || userId.isBlank())
                || (password == null || password.isBlank())
                || (email == null || email.isBlank())) {
            throw new IllegalArgumentException();
        }
        this.userId = userId;
        this.password = password;
        this.email = email;
        if (name != null && !name.isBlank()) {
            this.name = name;
        } else if (name != null && name.isBlank()) {
            this.name = null;
        }
    }

    public String getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void update(String password, String email, String name) {
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
        if (email != null && !email.isBlank()) {
            this.email = email;
        }

        if (name != null && !name.isBlank()) {
            this.name = name;
        } else if (name != null && name.isBlank()) {
            this.name = null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return id != null && Objects.equals(id, user.id);
    }
}
