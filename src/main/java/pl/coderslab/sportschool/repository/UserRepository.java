package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportschool.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
