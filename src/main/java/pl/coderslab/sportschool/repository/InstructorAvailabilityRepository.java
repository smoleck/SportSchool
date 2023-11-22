package pl.coderslab.sportschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportschool.model.InstructorAvailability;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InstructorAvailabilityRepository extends JpaRepository<InstructorAvailability, Long> {
    // Dodaj dodatkowe metody repozytorium, jeśli są potrzebne
    List<InstructorAvailability> findByInstructor_Id(Long instructorId);
    List<InstructorAvailability> findByInstructorIdAndAvailabilityDate(Long instructorId, LocalDate availabilityDate);

}
