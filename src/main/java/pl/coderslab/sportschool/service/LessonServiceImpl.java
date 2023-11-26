package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;
import pl.coderslab.sportschool.repository.LessonRepository;
import pl.coderslab.sportschool.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> getLessonsByInstructor(Instructor instructor) {
        return lessonRepository.findByInstructor(instructor);
    }

    @Override
    public List<Lesson> getLessonsByStudent(Student student) {
        return lessonRepository.findByStudentsContaining(student);
    }


    @Override
    public List<Lesson> getLessonsByDateTime(LocalDate lessonDate) {
        return lessonRepository.findByLessonDate(lessonDate);
    }

    @Override
    public void addLesson(Instructor instructor, LocalDate LessonDate, LocalTime startTime, List<Student> students, boolean isGroup, User createdBy) {
        Lesson lesson = new Lesson();
        lesson.setInstructor(instructor);
        lesson.setLessonDate(LessonDate);
        lesson.setStartTime(startTime);
        lesson.getStudents().addAll(students);
        lesson.setCreatedBy(createdBy);

        lesson.setIsGroup(isGroup);

        // Ustaw cenę w zależności od warunków
        if (isGroup) {
            lesson.setPrice(new BigDecimal("80")); // Cena stała dla lekcji grupowej
        } else {
            int numberOfStudents = students.size();
            BigDecimal price;

            if (numberOfStudents == 1) {
                price = new BigDecimal("100");
            } else if (numberOfStudents == 2) {
                price = new BigDecimal("180");
            } else if (numberOfStudents >= 3) {
                price = new BigDecimal("250");
            } else {
                // Dodaj obsługę sytuacji, gdy liczba kursantów jest mniejsza niż 1
                throw new IllegalArgumentException("Liczba kursantów mniejsza niż 1");
            }

            lesson.setPrice(price);
        }

        lessonRepository.save(lesson);

    }
    @Override
    public List<Lesson> getLessonsCreatedByLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            User loggedInUser = userRepository.findByUsername(username);

            if (loggedInUser != null) {
                return lessonRepository.findByCreatedBy(loggedInUser);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}
