package pl.coderslab.sportschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.sportschool.model.Instructor;
import pl.coderslab.sportschool.repository.InstructorRepository;

import java.util.List;
import java.util.Optional;

@Service("instructorService")
public class InstructorService implements UserDetailsService {

    private final InstructorRepository instructorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, BCryptPasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Instructor instructor = instructorRepository.findByUsername(username);
        if (instructor == null) {
            throw new UsernameNotFoundException("Instructor not found with username: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(instructor.getUsername())
                .password(instructor.getPassword())
                .roles("INSTRUCTOR")
                .build();
    }

    public void addInstructor(String name, String surname, String email, String phone, String username, String password) {
        Instructor instructor = new Instructor();
        instructor.setName(name);
        instructor.setSurname(surname);
        instructor.setEmail(email);
        instructor.setPhone(phone);
        instructor.setUsername(username);
        instructor.setRole("INSTRUCTOR");
        instructor.setPassword(passwordEncoder.encode(password));
        instructorRepository.save(instructor);
    }

    public Instructor getInstructorByUsername(String username) {
        return instructorRepository.findByUsername(username);
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
    public Instructor getInstructorById(Long instructorId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        return optionalInstructor.orElse(null);
    }

}
