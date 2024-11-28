package ru.costa.tours_vue_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.costa.tours_vue_jwt.entity.Password;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
