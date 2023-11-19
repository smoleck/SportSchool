package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.repository.LessonRepository;

import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public void addLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }

    // Dodaj inne metody serwisu, jeśli są potrzebne
}
