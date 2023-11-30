package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByInstructor(Instructor instructor);

    List<Lesson> findByStudentsContaining(Student student);

    List<Lesson> findByLessonDate(LocalDate lessonDate);// Możesz dodać niestandardowe zapytania lub korzystać z gotowych metod dostarczanych przez JpaRepository

    List<Lesson> findByCreatedBy(User user);

    @Query("SELECT l FROM Lesson l ORDER BY l.lessonDate DESC")
    List<Lesson> findAllOrderedByLessonDateDesc();
    List<Lesson> findByIsGroup(boolean isGroup);
    @Query("SELECT l FROM Lesson l WHERE l.lessonDate > CURRENT_DATE AND l.isGroup = true")
    List<Lesson> findFutureGroupLessons();
    @Query("SELECT l FROM Lesson l WHERE l.instructor.username = :instructorName AND l.lessonDate > CURRENT_DATE ORDER BY l.lessonDate ASC")
    Lesson findNearestLessonForInstructorByName(String instructorName);

    List<Lesson> findByInstructorUsername(String instructorUsername);
}
