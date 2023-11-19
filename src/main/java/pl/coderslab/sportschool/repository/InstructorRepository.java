package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportschool.model.Instructor;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
     Instructor findByUsername(String username);
}

