package pl.coderslab.sportschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.service.InstructorService;
import pl.coderslab.sportschool.service.LessonService;

import java.util.List;

@Controller
@RequestMapping("/user/lesson")
public class LessonController {

    private final InstructorService instructorService;
    private final LessonService lessonService;

    @Autowired
    public LessonController(InstructorService instructorService, LessonService lessonService) {
        this.instructorService = instructorService;
        this.lessonService = lessonService;
    }

    @GetMapping("/add")
    public String showAddLessonForm(Model model) {
        List<Instructor> instructors = instructorService.getAllInstructors();
        model.addAttribute("instructors", instructors);
        model.addAttribute("lesson", new Lesson());

        return "addLessonForm";
    }

    @PostMapping("/add")
    public String addLesson(@ModelAttribute("lesson") Lesson lesson) {
        Instructor selectedInstructor = instructorService.getInstructorById(lesson.getInstructor().getId());
        lesson.setInstructor(selectedInstructor);

        // Ustaw odpowiednie wartości dla nowych pól
        lesson.setIsGroup(lesson.isGroup());
        lesson.setLessonDate(lesson.getLessonDate());
        lesson.setStartTime(lesson.getStartTime());
        lesson.setEndTime(lesson.getEndTime());

        lessonService.addLesson(lesson);

        return "redirect:/user/home";
    }

//    @GetMapping("/list")
//    public String showAllLessons(Model model) {
//        List<Lesson> lessons = lessonService.getAllLessons();
//        model.addAttribute("lessons", lessons);
//
//        return "lessonList";
//    }
}
