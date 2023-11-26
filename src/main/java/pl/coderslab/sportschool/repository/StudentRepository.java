package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByUser(User user);
    List<Student> findAllById(Iterable<Long> studentIds);

}
