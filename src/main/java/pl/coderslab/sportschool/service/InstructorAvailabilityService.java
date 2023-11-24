package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.model.InstructorAvailability;
import pl.coderslab.sportschool.repository.InstructorAvailabilityRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public InstructorAvailability getAvailabilityById(Long availabilityId) {
        Optional<InstructorAvailability> optionalAvailability = availabilityRepository.findById(availabilityId);
        return optionalAvailability.orElse(null);
    }
    public List<InstructorAvailability> getAvailabilityForInstructor(Long instructorId) {
        return availabilityRepository.findByInstructor_Id(instructorId);
    }
    public List<LocalTime> getInstructorAvailability(Long instructorId, LocalDate availabilityDate) {
        List<InstructorAvailability> availabilities = availabilityRepository.findByInstructorIdAndAvailabilityDate(instructorId, availabilityDate);

        // Mapuj obiekty InstructorAvailability na listę LocalTime
        List<LocalTime> availabilityHours = availabilities.stream()
                .map(InstructorAvailability::getStartTime) // Zakładając, że StartTime to LocalTime
                .collect(Collectors.toList());


        return availabilityHours;
    }

    @Transactional
    public void removeInstructorAvailability(Long instructorId, LocalDate availabilityDate, LocalTime startTime) {
        availabilityRepository.deleteByInstructor_IdAndAvailabilityDateAndStartTime(instructorId, availabilityDate, startTime);
    }

    // Dodaj inne metody serwisu, jeśli są potrzebne
}
