package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

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

    public Student getStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent.orElse(null);
    }
}
