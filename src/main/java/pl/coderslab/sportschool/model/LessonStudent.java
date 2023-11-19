//package pl.coderslab.sportschool.model;
//
//import javax.persistence.*;
//
//
//@Entity
//@Table(name = "lesson_students")
//public class LessonStudent {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "lesson_id")
//    private Lesson lesson;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
//
//    // Getters and setters
//
//    // Constructors
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Lesson getLesson() {
//        return lesson;
//    }
//
//    public void setLesson(Lesson lesson) {
//        this.lesson = lesson;
//    }
//
//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }
//
//    public LessonStudent() {
//    }
//
//    public LessonStudent(Long id, Lesson lesson, Student student) {
//        this.id = id;
//        this.lesson = lesson;
//        this.student = student;
//    }
//}
