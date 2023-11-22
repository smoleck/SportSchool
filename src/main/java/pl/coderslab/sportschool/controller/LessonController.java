package pl.coderslab.sportschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.InstructorAvailability;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.service.InstructorService;
import pl.coderslab.sportschool.service.LessonService;
import pl.coderslab.sportschool.service.InstructorAvailabilityService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user/lesson")
public class LessonController {

    private final InstructorService instructorService;
    private final LessonService lessonService;
    private  final InstructorAvailabilityService instructorAvailabilityService;

    @Autowired
    public LessonController(InstructorService instructorService, LessonService lessonService, InstructorAvailabilityService instructorAvailabilityService) {
        this.instructorService = instructorService;
        this.lessonService = lessonService;
        this.instructorAvailabilityService = instructorAvailabilityService;
    }

    @GetMapping("/add")
    public String showAddLessonForm(Model model) {
        List<Instructor> instructors = instructorService.getAllInstructors();
        model.addAttribute("instructors", instructors);
        model.addAttribute("lesson", new Lesson());

        return "addLessonForm";
    }

    @GetMapping("/select-availability")
    public String showAvailabilityForm(Model model) {
        // Pobierz listę dostępnych instruktorów
        List<Instructor> instructors = instructorService.getAllInstructors();
        model.addAttribute("instructors", instructors);

        return "availability-form";
    }

    @PostMapping("/select-availability")
    public String showInstructorAvailability(@RequestParam Long instructorId,
                                             @RequestParam String availabilityDate,
                                             Model model) {
        // Pobierz dostępności instruktora dla wybranej daty
        LocalDate date = LocalDate.parse(availabilityDate);
        List<InstructorAvailability> availabilities = instructorAvailabilityService.getInstructorAvailability(instructorId, date);

        model.addAttribute("availabilities", availabilities);

        return "availability-table";
    }


}
