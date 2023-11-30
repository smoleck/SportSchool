package pl.coderslab.sportschool.service;

import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface LessonService {

    List<Lesson> getAllLessons();

    Lesson getLessonById(Long id);

    List<Lesson> getLessonsByInstructor(Instructor instructor);

    List<Lesson> getLessonsByStudent(Student student);

    List<Lesson> getLessonsByDateTime(LocalDate lessonDate);

    void addLesson(Instructor instructor, LocalDate LessonDate, LocalTime startTime, LocalTime endTime, List<Student> students, boolean isGroup, User user) ;

    List<Lesson> getLessonsCreatedByLoggedInUser();
    List<Lesson> findAllOrderedByLessonDateDesc();

    public List<Lesson> getGroupLessons();
    void updateLesson(Lesson lesson);
    public void saveLesson(Lesson lesson);

    void deleteLesson(Long id);
    void removeStudentFromLesson(Long lessonId, Long studentId);
    public void addStudentToLesson(Long lessonId, Long studentId);
    List<Lesson> getFutureGroupLessons();

    public Lesson getNextLessonForInstructor(String instructorName);

    List<Lesson> getAllInstructorLessons(String instructorUsername);


}
