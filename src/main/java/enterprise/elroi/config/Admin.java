package enterprise.elroi.config;

import enterprise.elroi.data.models.User;
import enterprise.elroi.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Admin implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@elroi.com")) {
            User admin = new User();
            admin.setEmail("admin@elroi.com");
            admin.setFirstName("Elroi");
            admin.setLastName("Admin");
            admin.setRole("ADMIN");
            admin.setPhoneNumber("0000000000");
            admin.setAddress("Admin Address");
            userRepository.save(admin);

            System.out.println("========================================");
            System.out.println("   Admin account created successfully   ");
            System.out.println("   Email:    admin@elroi.com            ");
            System.out.println("   Role:     ADMIN                      ");
            System.out.println("========================================");
        } else {
            System.out.println("Admin account already exists — skipping seeding.");
        }
    }
}