//package pl.coderslab.sportschool.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import pl.coderslab.sportschool.model.LessonStudent;
//import pl.coderslab.sportschool.repository.LessonStudentRepository;
//
//import java.util.List;
//
//@Service
//public class LessonStudentService {
//
//    @Autowired
//    private LessonStudentRepository lessonStudentRepository;
//
//    // Inne metody serwisu
//
//    public LessonStudent saveLessonStudent(LessonStudent lessonStudent) {
//        return lessonStudentRepository.save(lessonStudent);
//    }
//
//    public List<LessonStudent> getLessonStudentsByLessonId(Long lessonId) {
//        return lessonStudentRepository.findByLesson_Id(lessonId);
//    }
//
//    // Dodaj inne metody serwisu, jeśli są potrzebne
//}
