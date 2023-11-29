package pl.coderslab.sportschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.Lesson;
import pl.coderslab.sportschool.model.Student;
import pl.coderslab.sportschool.service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/lesson")
public class AdminLessonController {

    private final LessonService lessonService;
    private final InstructorService instructorService;
    private final StudentService studentService;

    @Autowired
    public AdminLessonController(LessonService lessonService, InstructorService instructorService, StudentService studentService) {
        this.lessonService = lessonService;
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public String showAllLessons(Model model) {
        // Pobierz wszystkie lekcje i przekaż je do widoku
        List<Lesson> allLessons = lessonService.getAllLessons();
        model.addAttribute("allLessons", allLessons);


        return "allLessons";
    }

    // Metoda do wyświetlania formularza edycji lekcji
    @GetMapping("/edit/{lessonId}")
    public String showAllStudentsForLesson(@PathVariable Long lessonId, Model model) {
        Lesson lesson = lessonService.getLessonById(lessonId);

        List<Student> allStudentsForLesson = new ArrayList<>(lesson.getStudents());
        List<Student> allStudentsNotInLesson = studentService.getAllStudentsNotInLesson(lesson); // Dostarcz odpowiednią implementację tej metody

        model.addAttribute("lesson", lesson);
        model.addAttribute("allStudentsForLesson", allStudentsForLesson);
        model.addAttribute("allStudentsNotInLesson", allStudentsNotInLesson); // Dodaj listę studentów, którzy nie są jeszcze w lekcji

        return "editLessonForm";
    }
    @PostMapping("/edit/{lessonId}/addStudent")
    public String addStudentToLesson(@PathVariable Long lessonId, @RequestParam Long newStudentId) {
        lessonService.addStudentToLesson(lessonId, newStudentId);
        return "redirect:/admin/lesson/edit/" + lessonId;
    }


    @PostMapping("/edit/{lessonId}/removeStudent")
    public String removeStudentFromLesson(@PathVariable Long lessonId,
                                          @RequestParam Long studentId) {
        lessonService.removeStudentFromLesson(lessonId, studentId);
        return "redirect:/admin/lesson/edit/{lessonId}";
    }


    @GetMapping("/delete/{lessonId}")
    public String deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return "redirect:/admin/lesson/all";
    }


}
