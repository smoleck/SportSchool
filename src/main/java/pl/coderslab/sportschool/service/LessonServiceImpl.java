package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.repository.LessonRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
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
    public void addLesson(Instructor instructor, LocalDate LessonDate, LocalTime startTime, List<Student> students, boolean isGroup) {
        Lesson lesson = new Lesson();
        lesson.setInstructor(instructor);
        lesson.setLessonDate(LessonDate);
        lesson.setStartTime(startTime);
        lesson.getStudents().addAll(students);

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
    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}
