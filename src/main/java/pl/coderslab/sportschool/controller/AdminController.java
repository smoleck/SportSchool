package pl.coderslab.sportschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.service.InstructorService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final InstructorService instructorService;

    public AdminController(InstructorService instructorService) {
        this.instructorService = instructorService;
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


}
