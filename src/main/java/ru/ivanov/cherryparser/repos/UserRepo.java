package ru.ivanov.cherryparser.repos;

import ru.ivanov.cherryparser.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
