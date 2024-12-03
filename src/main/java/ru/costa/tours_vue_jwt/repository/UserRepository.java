package ru.costa.tours_vue_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.costa.tours_vue_jwt.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
    Optional<User> findByLastName(String lastName);
    Optional<User> findById(Long id);
    boolean existsByUsername(String username);
}
