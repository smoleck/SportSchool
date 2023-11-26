package pl.coderslab.sportschool.service;

import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface LessonService {

    List<Lesson> getAllLessons();

    Lesson getLessonById(Long id);

    List<Lesson> getLessonsByInstructor(Instructor instructor);

    List<Lesson> getLessonsByStudent(Student student);

    List<Lesson> getLessonsByDateTime(LocalDate lessonDate);

    void addLesson(Instructor instructor, LocalDate LessonDate, LocalTime startTime, List<Student> students, boolean isGroup, User user) ;

    List<Lesson> getLessonsCreatedByLoggedInUser();

    void updateLesson(Lesson lesson);

    void deleteLesson(Long id);



}
