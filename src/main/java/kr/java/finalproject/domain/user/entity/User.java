package kr.java.finalproject.domain.user.entity;

import jakarta.persistence.*;
import kr.java.finalproject.domain.auth.entity.SocialAccount;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    @PrePersist
    public void generatePublicId() {
        this.publicId = UUID.randomUUID();
    }

    public User(String email) {
        this.email = email;
    }
}