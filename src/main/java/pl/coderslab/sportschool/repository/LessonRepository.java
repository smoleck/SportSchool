package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;

import java.time.LocalDate;
import java.util.List;

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
    @Query("SELECT l FROM Lesson l WHERE l.lessonDate >= CURRENT_DATE AND (l.lessonDate > CURRENT_DATE OR (l.lessonDate = CURRENT_DATE AND l.startTime > CURRENT_TIME)) ORDER BY l.lessonDate ASC, l.startTime ASC")
    List<Lesson> nextLessonForInstructorByName(String instructorName);

    List<Lesson> findByInstructorUsername(String instructorUsername);
}
