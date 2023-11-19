package pl.coderslab.sportschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.InstructorAvailability;
import pl.coderslab.sportschool.service.InstructorAvailabilityService;
import pl.coderslab.sportschool.service.InstructorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/instructorAvailability")
public class InstructorAvailabilityController {

    private final InstructorService instructorService;
    private final InstructorAvailabilityService availabilityService;

    @Autowired
    public InstructorAvailabilityController(InstructorService instructorService, InstructorAvailabilityService availabilityService) {
        this.instructorService = instructorService;
        this.availabilityService = availabilityService;
    }

    @GetMapping("/add")
    public String showAddAvailabilityForm(Model model) {
        List<Instructor> instructors = instructorService.getAllInstructors();
        model.addAttribute("instructors", instructors);
        model.addAttribute("availability", new InstructorAvailability());

        return "addInstructorAvailabilityForm";
    }

    @PostMapping("/add")
    public String addInstructorAvailability(@ModelAttribute("availability") InstructorAvailability availability) {
        // Pobierz instruktora na podstawie ID z formularza
        Instructor selectedInstructor = instructorService.getInstructorById(availability.getInstructor().getId());

        // Ustaw instruktora w dostępności
        availability.setInstructor(selectedInstructor);

        // Dodaj dostępność do serwisu
        availabilityService.addInstructorAvailability(
                availability.getInstructor(),
                availability.getAvailabilityDate(),
                availability.getStartTime(),
                availability.getEndTime()
        );

        return "redirect:/admin/instructorAvailability/add?success";
    }
}
