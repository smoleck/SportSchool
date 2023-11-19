//package pl.coderslab.sportschool;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import pl.coderslab.sportschool.service.UserService;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserService userService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    public DataInitializer(UserService userService, BCryptPasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Dodaj użytkownika "admin" z zakodowanym hasłem i rolą "ADMIN" (jeśli nie istnieje)
//        userService.addUser("admin", passwordEncoder.encode("admin"), "ADMIN");
//        userService.addUser("user", passwordEncoder.encode("user"), "USER");
//        userService.addUser("instructor", passwordEncoder.encode("instructor"), "INSTRUCTOR");
//    }
//}
