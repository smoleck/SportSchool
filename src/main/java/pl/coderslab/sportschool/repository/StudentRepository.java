package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportschool.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
