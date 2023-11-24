package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.repository.LessonRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public void addLesson(Instructor instructor, LocalDate LessonDate, LocalTime startTime) {
        Lesson lesson = new Lesson();
        lesson.setInstructor(instructor);
        lesson.setLessonDate(LessonDate);
        lesson.setStartTime(startTime);
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
