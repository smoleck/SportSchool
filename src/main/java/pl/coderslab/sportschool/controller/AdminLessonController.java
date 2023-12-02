package pl.coderslab.sportschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;
import pl.coderslab.sportschool.service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/lesson")
public class AdminLessonController {

    private final LessonService lessonService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final InstructorAvailabilityService instructorAvailabilityService;
    private final UserService userService;

    @Autowired
    public AdminLessonController(LessonService lessonService, InstructorService instructorService, StudentService studentService, InstructorAvailabilityService instructorAvailabilityService, UserService userService) {
        this.lessonService = lessonService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.instructorAvailabilityService = instructorAvailabilityService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String showAllLessons(Model model) {
        // Pobierz wszystkie lekcje i przekaż je do widoku
        List<Lesson> allLessons = lessonService.getAllLessons();
        model.addAttribute("allLessons", allLessons);


        return "allLessons";
    }

    // Metoda do wyświetlania formularza edycji lekcji
    @GetMapping("/edit/{lessonId}")
    public String showAllStudentsForLesson(@PathVariable Long lessonId, Model model) {
        Lesson lesson = lessonService.getLessonById(lessonId);

        List<Student> allStudentsForLesson = new ArrayList<>(lesson.getStudents());
        List<Student> allStudentsNotInLesson = studentService.getAllStudentsNotInLesson(lesson); // Dostarcz odpowiednią implementację tej metody

        model.addAttribute("lesson", lesson);
        model.addAttribute("allStudentsForLesson", allStudentsForLesson);
        model.addAttribute("allStudentsNotInLesson", allStudentsNotInLesson); // Dodaj listę studentów, którzy nie są jeszcze w lekcji

        return "editLessonForm";
    }
    @PostMapping("/edit/{lessonId}/addStudent")
    public String addStudentToLesson(@PathVariable Long lessonId, @RequestParam Long newStudentId) {
        lessonService.addStudentToLesson(lessonId, newStudentId);
        return "redirect:/admin/lesson/edit/" + lessonId;
    }


    @PostMapping("/edit/{lessonId}/removeStudent")
    public String removeStudentFromLesson(@PathVariable Long lessonId,
                                          @RequestParam Long studentId) {
        lessonService.removeStudentFromLesson(lessonId, studentId);
        return "redirect:/admin/lesson/edit/{lessonId}";
    }


    @GetMapping("/delete/{lessonId}")
    public String deleteLesson(@PathVariable Long lessonId) {
        Lesson lesson= lessonService.getLessonById(lessonId);
        instructorAvailabilityService.addInstructorAvailability(lesson.getInstructor(), lesson.getLessonDate(),lesson.getStartTime(), lesson.getEndTime());

        lessonService.deleteLesson(lessonId);
        return "redirect:/admin/lesson/all";
    }

    @GetMapping("/add")
    public String showLessonForm(Model model) {
        // Pobierz listę wszystkich instruktorów
        List<Instructor> instructors = instructorService.getAllInstructors();

        // Pobierz listę wszystkich kursantów (użytkowników)
        List<Student> students = studentService.getAllStudents();


        // Przekaż instruktorów i kursantów do widoku
        model.addAttribute("instructors", instructors);
        model.addAttribute("students", students);
        model.addAttribute("isGroup", false);

        // Zwróć nazwę widoku, gdzie użytkownik wybierze instruktora, datę, godzinę i kursantów
        return "adminAddLessonForm";
    }

    @GetMapping("/availability")
    @ResponseBody
    public List<LocalTime> getInstructorAvailability(@RequestParam String date, @RequestParam Long instructorId) {
        // Pobierz dostępność instruktora w danym dniu z serwisu
        LocalDate lessonDate = LocalDate.parse(date); // Konwersja stringa na LocalDate
        List<LocalTime> availableHours = instructorAvailabilityService.getInstructorAvailability(instructorId, lessonDate);

        return availableHours;
    }
    @PostMapping("/save")
    public String saveLesson(@RequestParam Long instructorId,
                             @RequestParam String lessonName,
                             @RequestParam String lessonDate,
                             @RequestParam String lessonTime,
                             @RequestParam String endTime,
                             @RequestParam boolean isGroup,
                             @RequestParam(required = false) List<Long> studentIds) {
        Instructor instructor = instructorService.getInstructorById(instructorId);

        // Pobierz zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User loggedInUser = userService.getUserByUsername(username);

        // Pobierz kursantów na podstawie ich ID, jeśli lista studentIds nie jest null
        List<Student> students = (studentIds != null) ? userService.getStudentsByIds(studentIds) : Collections.emptyList();

        // Dodaj lekcję z uwzględnieniem kursantów
        lessonService.addLesson(instructor,lessonName, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime), LocalTime.parse(endTime), students, isGroup, loggedInUser);

        // Usuń dostępność instruktora (jeśli to konieczne)
        instructorAvailabilityService.removeInstructorAvailability(instructorId, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime));

        // Przekieruj użytkownika na stronę po zapisaniu lekcji
        return "redirect:/admin/lesson/all";
    }

}
