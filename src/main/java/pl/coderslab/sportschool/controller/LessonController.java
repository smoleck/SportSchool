package pl.coderslab.sportschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.InstructorAvailability;
import pl.coderslab.sportschool.service.InstructorService;
import pl.coderslab.sportschool.service.LessonService;
import pl.coderslab.sportschool.service.InstructorAvailabilityService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/user/lesson")
public class LessonController {

    @Autowired
    private InstructorService instructorService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private InstructorAvailabilityService instructorAvailabilityService;

//    @Autowired
//    public LessonController(InstructorService instructorService, LessonService lessonService, InstructorAvailabilityService instructorAvailabilityService) {
//        this.instructorService = instructorService;
//        this.lessonService = lessonService;
//        this.instructorAvailabilityService = instructorAvailabilityService;
//    }

    @GetMapping("/add")
    public String showLessonForm(Model model) {
        // Pobierz listę wszystkich instruktorów
        List<Instructor> instructors = instructorService.getAllInstructors();

        // Przekaż instruktorów do widoku
        model.addAttribute("instructors", instructors);

        // Zwróć nazwę widoku, gdzie użytkownik wybierze instruktora, datę i godzinę
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
    public String saveLesson(@RequestParam Long instructorId, @RequestParam String lessonDate, @RequestParam String lessonTime) {
        // Pobierz instruktora na podstawie ID
        Instructor instructor = instructorService.getInstructorById(instructorId);

        // Przetworzenie i zapisanie lekcji w bazie danych
        lessonService.addLesson(instructor, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime));

        instructorAvailabilityService.removeInstructorAvailability(instructorId, LocalDate.parse(lessonDate), LocalTime.parse(lessonTime));
        // Przekieruj użytkownika na stronę po zapisaniu lekcji
        return "redirect:/user/home";
    }
}
//