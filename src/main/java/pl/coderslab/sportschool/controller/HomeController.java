package pl.coderslab.sportschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/instructor/home")
    public String instructorHome() {
        return "instructorHome";
    }


}
