package pl.coderslab.sportschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;
import pl.coderslab.sportschool.service.UserService;
import pl.coderslab.sportschool.service.StudentService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;
    private final StudentService studentService;

    @Autowired
    public UserController(UserService userService, StudentService studentService) {
        this.userService = userService;
        this.studentService = studentService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "addUserForm";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (!userService.isUsernameUnique(user.getUsername())) {
            result.rejectValue("username", "error.user", "Nazwa użytkownika już istnieje");
        }

        if (result.hasErrors()) {
            return "addUserForm";
        }

        userService.addUser(user.getUsername(), user.getPassword(), user.getRole(), user.getEmail(), user.getPhoneNumber());
        return "redirect:/login";
    }
    @GetMapping("/user/home")
    public String userHome(Model model,Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        userService.getUserByUsername(username);
        return "userHome";
    }

    @GetMapping("/user/addStudent")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "addStudentForm";
    }

    @PostMapping("/user/addStudent")
    public String addStudent(@ModelAttribute("student") Student student, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        student.setUser(user);
        studentService.addStudent(student);
        return "redirect:/user/home";
    }
}
