package pl.coderslab.sportschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.service.InstructorService;
import pl.coderslab.sportschool.service.LessonServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InstructorService instructorService;
    private final LessonServiceImpl lessonService;

    public AdminController(InstructorService instructorService, LessonServiceImpl lessonService) {
        this.instructorService = instructorService;
        this.lessonService = lessonService;
    }

    @GetMapping("/home")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/addInstructorForm")
    public String showAddInstructorForm(Model model) {
        // Przekazanie obiektu Instructor do widoku
        model.addAttribute("instructor", new Instructor());
        return "addInstructorForm";
    }

    @PostMapping("/addInstructorForm")
    public String addInstructor(Instructor instructor) {
        // Obsługa dodawania instruktora
        instructorService.addInstructor(instructor.getName(), instructor.getSurname(),
                instructor.getEmail(), instructor.getPhone(), instructor.getUsername(),
                instructor.getPassword());
        return "redirect:/admin/addInstructorForm?success";
    }

    @GetMapping("/instructorPayout")
    public String instructorPayout(Model model){
        instructorService.updateEarningsForCompletedLessonsForAll();
        List<Instructor> allInstructors = instructorService.getAllInstructors();
        model.addAttribute("allInstructors", allInstructors);

        return "instructorPayout";
    }

    @PostMapping("/instructorPayout/resetEarnings")
    public String resetEarnings(@RequestParam Long instructorId) {
        // Resetuj zarobki i ustaw lastResetDateTime
        instructorService.resetEarnings(instructorId);

        // Przekieruj użytkownika z powrotem do strony z listą instruktorów po zresetowaniu
        return "redirect:/admin/instructorPayout";
    }

}
