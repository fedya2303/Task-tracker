package by.bntu.backend.repository;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
