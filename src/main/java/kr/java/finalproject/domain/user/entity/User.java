package kr.java.finalproject.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID publicId;

    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID();
    }

    public User(String email) {
        this.email = email;
    }
}