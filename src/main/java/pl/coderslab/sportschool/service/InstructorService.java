package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.repository.InstructorRepository;
import pl.coderslab.sportschool.repository.LessonRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service("instructorService")
public class InstructorService implements UserDetailsService {

    private final InstructorRepository instructorRepository;
    private final LessonRepository lessonRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, LessonRepository lessonRepository, BCryptPasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.lessonRepository = lessonRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Instructor instructor = instructorRepository.findByUsername(username);
        if (instructor == null) {
            throw new UsernameNotFoundException("Instructor not found with username: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(instructor.getUsername())
                .password(instructor.getPassword())
                .roles("INSTRUCTOR")
                .build();
    }

    public void addInstructor(String name, String surname, String email, String phone, String username, String password) {
        Instructor instructor = new Instructor();
        instructor.setName(name);
        instructor.setSurname(surname);
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructor.setUsername(username);
        instructor.setRole("INSTRUCTOR");
        instructor.setPassword(passwordEncoder.encode(password));
        instructorRepository.save(instructor);
        instructor.setLastResetDateTime(LocalDateTime.now());
    }

    public Instructor getInstructorByUsername(String username) {
        return instructorRepository.findByUsername(username);
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
    public Instructor getInstructorById(Long instructorId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        return optionalInstructor.orElse(null);
    }


    public void updateEarningsForCompletedLessons(String instructorUsername) {
        Instructor instructor = instructorRepository.findByUsername(instructorUsername);
        instructor.setEarnings(0L);

        Long earnings = instructor.getEarnings();
        LocalDateTime lastResetDateTime = instructor.getLastResetDateTime();

        List<Lesson> lessons = lessonRepository.findByInstructor(instructor);

        for (Lesson lesson : lessons) {
            LocalDateTime lessonEndDateTime = (lesson.getLessonDate()).atTime(lesson.getEndTime());
            List<Student> students = lesson.getStudents();
            int numberOfStudents = students.size();


            // Sprawdź, czy lekcja już się odbyła (czy czas zakończenia lekcji jest po ostatnim zresetowaniu zarobków)
            if (lessonEndDateTime.isAfter(lastResetDateTime) && lessonEndDateTime.isBefore(LocalDateTime.now())){
                if (lesson.isGroup()) {
                    earnings += 80;
                } else {///
                    if (numberOfStudents == 1) {
                        earnings += 50;
                    } else if (numberOfStudents == 2) {
                        earnings += 70;
                    } else if (numberOfStudents == 3) {
                        earnings += 90;
                    }
                    // Możesz dodać więcej warunków, jeżeli liczba kursantów jest większa niż 3
                }
            }
        }

        instructor.setEarnings(earnings);
        instructorRepository.save(instructor);
    }
    public void updateEarningsForCompletedLessonsForAll() {
        List<Instructor> allInstructors = instructorRepository.findAll();

        for (Instructor instructor : allInstructors) {
            updateEarningsForCompletedLessons(instructor.getUsername());
        }
    }

    public void resetEarnings(Long instructorId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);

        optionalInstructor.ifPresent(instructor -> {
            // Ustaw datę i czas ostatniego zerowania zarobków na aktualną datę i czas
            instructor.setLastResetDateTime(LocalDateTime.now());
            instructor.setEarnings(0L);

            instructorRepository.save(instructor);
        });
    }
}
