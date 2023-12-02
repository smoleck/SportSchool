package pl.coderslab.sportschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.*;
import pl.coderslab.sportschool.service.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/lesson")
public class LessonController {


    private final InstructorService instructorService;

    private final LessonServiceImpl lessonService;

    private final InstructorAvailabilityService instructorAvailabilityService;

    private final UserService userService;
    private final StudentService studentService;
    @Autowired
    public LessonController(InstructorService instructorService, LessonServiceImpl lessonService, InstructorAvailabilityService instructorAvailabilityService, UserService userService, StudentService studentService) {
        this.instructorService = instructorService;
        this.lessonService = lessonService;
        this.instructorAvailabilityService = instructorAvailabilityService;
        this.userService = userService;
        this.studentService = studentService;
    }


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
                             @RequestParam String lessonName,
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
        lessonService.addLesson(instructor,lessonName, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime), LocalTime.parse(endTime), students, isGroup, loggedInUser);
        // Usuń dostępność instruktora (jeśli to konieczne)
        instructorAvailabilityService.removeInstructorAvailability(instructorId, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime));

        // Przekieruj użytkownika na stronę po zapisaniu lekcji
        return "redirect:/user/home";
    }

    @GetMapping("/created")
    public String showLessonsCreatedByLoggedInUser(Model model, Principal principal) {


        // Pobierz lekcje stworzone przez zalogowanego użytkownika
        List<Lesson> allLessons = lessonService.getAllLessons();



        List<Lesson> lessons = allLessons.stream()
                .filter(lesson -> lesson.getStudents().contains(userService.getStudentsCreatedByLoggedInUser()))
                .collect(Collectors.toList());
        // Przekaż lekcje do widoku
        model.addAttribute("lessons", lessons);

        // Zwróć nazwę widoku, gdzie będą wyświetlane lekcje
        return "lessonsCreatedByUser";
    }




    @GetMapping("/addToGroup")
    public String addToGroup(Model model){
        List<Lesson> futureGroupLessons = lessonService.getFutureGroupLessons();
        List<Student> students = userService.getStudentsCreatedByLoggedInUser();

        model.addAttribute("students", students);
        model.addAttribute("futureGroupLessons", futureGroupLessons);

        return "addToGroupForm";
    }

    @PostMapping("/addToGroup")
    public String addStudentsToLesson(@RequestParam Long lessonId,
                                      @RequestParam Long newStudentId) {
        // Dodaj wybranych studentów do lekcji o podanym ID
        lessonService.addStudentToLesson(lessonId, newStudentId);

        // Przekieruj użytkownika z powrotem do widoku lekcji grupowych
        return "redirect:/user/home";
    }
}
//