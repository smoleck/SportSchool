package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.InstructorAvailability;
import pl.coderslab.sportschool.repository.InstructorAvailabilityRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class InstructorAvailabilityService {

    private final InstructorAvailabilityRepository availabilityRepository;

    @Autowired
    public InstructorAvailabilityService(InstructorAvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public void addInstructorAvailability(Instructor instructor, LocalDate availabilityDate, LocalTime startTime, LocalTime endTime) {
        InstructorAvailability availability = new InstructorAvailability();
        availability.setInstructor(instructor);
        availability.setAvailabilityDate(availabilityDate);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);

        availabilityRepository.save(availability);
    }

    public List<InstructorAvailability> getAvailabilityForInstructor(Long instructorId) {
        return availabilityRepository.findByInstructor_Id(instructorId);
    }

    // Dodaj inne metody serwisu, jeśli są potrzebne
}
