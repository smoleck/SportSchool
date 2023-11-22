package pl.coderslab.sportschool.service;

import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LessonService {

    List<Lesson> getAllLessons();

    Lesson getLessonById(Long id);

    List<Lesson> getLessonsByInstructor(Instructor instructor);

    List<Lesson> getLessonsByStudent(Student student);

    List<Lesson> getLessonsByDateTime(LocalDateTime dateTime);

    void addLesson(Instructor instructor, List<Student> students, LocalDate LessonDate) ;

    void updateLesson(Lesson lesson);

    void deleteLesson(Long id);


}
