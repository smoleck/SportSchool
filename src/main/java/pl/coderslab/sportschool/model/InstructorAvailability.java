package pl.coderslab.sportschool.model;

import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.sportschool.model.Instructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "instructor_availability")
public class InstructorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;
    @Column(name = "availability_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
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

    public InstructorAvailability(Long id, Instructor instructor, LocalDate availabilityDate, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.instructor = instructor;
        this.availabilityDate = availabilityDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public InstructorAvailability() {
    }
}
