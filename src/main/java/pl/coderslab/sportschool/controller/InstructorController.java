package pl.coderslab.sportschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.service.InstructorService;
import pl.coderslab.sportschool.service.LessonService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final LessonService lessonService;
    private final InstructorService instructorService;

    public InstructorController(LessonService lessonService, InstructorService instructorService) {
        this.lessonService = lessonService;
        this.instructorService = instructorService;
    }

    @GetMapping("/home")
    public String instructorHome(Model model, Principal principal) {
    String instructorUsername = principal.getName();
    instructorService.updateEarningsForCompletedLessons(instructorUsername);
    Instructor instructor=instructorService.getInstructorByUsername(instructorUsername);
    Optional<Lesson> nextLesson = lessonService.getNextLessonForInstructor(instructorUsername).stream().findFirst();
    Long earnings=instructor.getEarnings();

    model.addAttribute("nextLesson", nextLesson.orElse(null));
    model.addAttribute("earnings", earnings);


        return "instructorHome";
    }
    @GetMapping("/lesson/all")
    public String showAllInstructorLessons(Model model, Principal principal) {
        String instructorName = principal.getName();

        // Pobierz wszystkie lekcje przypisane do tego instruktora
        List<Lesson> instructorLessons = lessonService.getAllInstructorLessons(instructorName);

        model.addAttribute("instructorLessons", instructorLessons);

        return "instructorLessons";  // To powinno odpowiadać nazwie twojego pliku HTML
    }

}
