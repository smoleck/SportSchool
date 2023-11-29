package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getStudentsByIds(List<Long> studentIds) {
        return studentRepository.findAllById(studentIds);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent.orElse(null);
    }

    public List<Student> getAllStudentsNotInLesson(Lesson lesson) {
        List<Student> allStudents = getAllStudents();// Pobierz wszystkich studentów z bazy danych, np. poprzez studentRepository.findAll()

                // Utwórz listę studentów, którzy nie są w danej lekcji
                List<Student> studentsNotInLesson = allStudents.stream()
                        .filter(student -> !lesson.getStudents().contains(student))
                        .collect(Collectors.toList());

        return studentsNotInLesson;
    }
}
