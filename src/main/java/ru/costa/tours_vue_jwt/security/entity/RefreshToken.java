package ru.costa.tours_vue_jwt.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.costa.tours_vue_jwt.entity.User;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", referencedColumnName = "user_id")
    private User user;
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @Column(name = "expiry_date")
    private Instant expiryDate;
}
