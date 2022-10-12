package com.study.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
public class User {

    @Id
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    @CreationTimestamp
    private LocalDateTime createDate;

    private String provider;
    private String providerId;

    public User() {
        super();
    }

    @Builder
    public User(String username, String password, String email, String role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
