package pl.coderslab.sportschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.service.LessonService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final LessonService lessonService;

    public InstructorController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/home")
    public String instructorHome(Model model, Principal principal) {
    String instructorUsername = principal.getName();
    Optional<Lesson> nextLesson = lessonService.getNextLessonForInstructor(instructorUsername).stream().findFirst();


    model.addAttribute("nextLesson", nextLesson.orElse(null));


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
