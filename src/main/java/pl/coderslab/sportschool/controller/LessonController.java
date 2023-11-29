package pl.coderslab.sportschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.*;
import pl.coderslab.sportschool.service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/user/lesson")
public class LessonController {

    @Autowired
    private InstructorService instructorService;
    @Autowired
    private LessonServiceImpl lessonService;
    @Autowired
    private InstructorAvailabilityService instructorAvailabilityService;
    @Autowired
    private UserService userService;


    @GetMapping("/add")
    public String showLessonForm(Model model) {
        // Pobierz listę wszystkich instruktorów
        List<Instructor> instructors = instructorService.getAllInstructors();

        // Pobierz listę wszystkich kursantów (użytkowników)
        List<Student> students = userService.getStudentsCreatedByLoggedInUser();


        // Przekaż instruktorów i kursantów do widoku
        model.addAttribute("instructors", instructors);
        model.addAttribute("students", students);

        // Zwróć nazwę widoku, gdzie użytkownik wybierze instruktora, datę, godzinę i kursantów
        return "addLessonForm";
    }

    @GetMapping("/availability")
    @ResponseBody
    public List<LocalTime> getInstructorAvailability(@RequestParam String date, @RequestParam Long instructorId) {
        // Pobierz dostępność instruktora w danym dniu z serwisu
        LocalDate lessonDate = LocalDate.parse(date); // Konwersja stringa na LocalDate
        List<LocalTime> availableHours = instructorAvailabilityService.getInstructorAvailability(instructorId, lessonDate);

        return availableHours;
    }
    // Dodaj metody do obsługi dodawania lekcji do bazy danych
    @PostMapping("/save")
    public String saveLesson(@RequestParam Long instructorId,
                             @RequestParam String lessonDate,
                             @RequestParam String lessonTime,
                             @RequestParam String endTime,
                             @RequestParam boolean isGroup,
                             @RequestParam List<Long> studentIds) {
        Instructor instructor = instructorService.getInstructorById(instructorId);

        // Pobierz zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User loggedInUser = userService.getUserByUsername(username);



        // Pobierz kursantów na podstawie ich ID
        List<Student> students = userService.getStudentsByIds(studentIds);

        // Dodaj lekcję z uwzględnieniem kursantów
        lessonService.addLesson(instructor, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime), LocalTime.parse(endTime), students, isGroup, loggedInUser);
        System.out.println("EndTime: " + endTime);
        // Usuń dostępność instruktora (jeśli to konieczne)
        instructorAvailabilityService.removeInstructorAvailability(instructorId, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime));

        // Przekieruj użytkownika na stronę po zapisaniu lekcji
        return "redirect:/user/home";
    }

    @GetMapping("/created")
    public String showLessonsCreatedByLoggedInUser(Model model) {


        // Pobierz lekcje stworzone przez zalogowanego użytkownika
        List<Lesson> lessons = lessonService.getLessonsCreatedByLoggedInUser();

        // Przekaż lekcje do widoku
        model.addAttribute("lessons", lessons);

        // Zwróć nazwę widoku, gdzie będą wyświetlane lekcje
        return "lessonsCreatedByUser";
    }
}
//