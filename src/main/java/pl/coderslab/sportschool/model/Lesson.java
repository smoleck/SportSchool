// src/main/java/pl/coderslab/sportschool/model/Lesson.java
package pl.coderslab.sportschool.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Column(name = "lesson_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lessonDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_group")
    private boolean isGroup;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "lesson_students",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Lesson(Long id, String name, Instructor instructor, LocalDate lessonDate, LocalTime startTime, LocalTime endTime, boolean isGroup, BigDecimal price, User createdBy, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isGroup = isGroup;
        this.price = price;
        this.createdBy = createdBy;
        this.students = students;
    }

    public Lesson(Long id, String name, Instructor instructor, LocalDate lessonDate, LocalTime startTime, LocalTime endTime, boolean isGroup, BigDecimal price, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isGroup = isGroup;
        this.price = price;
        this.students = students;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<Student> getStudents() {
        return students;
    }



    public boolean isGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean group) {
        isGroup = group;
    }

    public Lesson(Long id, String name, Instructor instructor, LocalDate lessonDate, LocalTime startTime, LocalTime endTime, boolean isGroup, Set<Student> students) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isGroup = isGroup;
        this.students = students;
    }

    public Lesson() {
    }
}
