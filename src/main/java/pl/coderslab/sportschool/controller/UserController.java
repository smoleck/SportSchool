package pl.coderslab.sportschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.model.User;
import pl.coderslab.sportschool.service.UserService;
import pl.coderslab.sportschool.service.StudentService;

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
    public String registerUser(@ModelAttribute("user") User user, Model model) {


        userService.addUser(user.getUsername(), user.getPassword(), user.getRole());
        return "redirect:/login";
    }
    @GetMapping("/user/home")
    public String userHome() {
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
