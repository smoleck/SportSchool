package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;
import pl.coderslab.sportschool.repository.InstructorRepository;
import pl.coderslab.sportschool.repository.LessonRepository;
import pl.coderslab.sportschool.repository.StudentRepository;
import pl.coderslab.sportschool.repository.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, UserRepository userRepository, StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
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
    public void addLesson(Instructor instructor, LocalDate LessonDate, LocalTime startTime, LocalTime endTime, List<Student> students, boolean isGroup, User createdBy) {
        Lesson lesson = new Lesson();
        lesson.setInstructor(instructor);
        lesson.setLessonDate(LessonDate);
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);
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


                lesson.setPrice(price);
            }
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
    public  List<Lesson> findAllOrderedByLessonDateDesc(){
        return lessonRepository.findAllOrderedByLessonDateDesc();
    }

    @Override
    public void addStudentToLesson(Long lessonId, Long studentId) {
        Lesson lesson = getLessonById(lessonId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();

            // Dodaj studenta do lekcji bez umieszczania logiki w modelu Lesson
            if (!lesson.getStudents().contains(student)) {
                lesson.getStudents().add(student);
                saveLesson(lesson);
            }
        } else {
            // Obsługa, gdy student o danym ID nie został znaleziony
            // Możesz rzucić wyjątek, zwrócić odpowiedni status itp.
        }
    }

    @Override
    @Transactional
    public void saveLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }
    @Transactional
    @Override
    public void removeStudentFromLesson(Long lessonId, Long studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        Student studentToRemove = studentRepository.findById(studentId).orElse(null);

        if (lesson != null && studentToRemove != null) {
            lesson.getStudents().remove(studentToRemove);
            studentToRemove.getLessons().remove(lesson);

            lessonRepository.save(lesson);
            studentRepository.save(studentToRemove);
        }
    }
    @Override
    public List<Lesson> getGroupLessons() {
        return lessonRepository.findByIsGroup(true);
    }

    @Override
    @Transactional
    public void updateLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    @Transactional
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
    @Override
    public List<Lesson> getFutureGroupLessons() {
        return lessonRepository.findFutureGroupLessons();
    }


    @Override
    public List<Lesson> getNextLessonForInstructor(String instructorName){
        return lessonRepository.nextLessonForInstructorByName(instructorName);
    }

    @Override
    public List<Lesson> getAllInstructorLessons(String instructorUsername) {
        // Pobierz wszystkie lekcje przypisane do danego instruktora
        return lessonRepository.findByInstructorUsername(instructorUsername);
    }

}
