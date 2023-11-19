package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportschool.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // Możesz dodać niestandardowe zapytania lub korzystać z gotowych metod dostarczanych przez JpaRepository
}
