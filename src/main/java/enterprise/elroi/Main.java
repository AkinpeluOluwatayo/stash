package enterprise.elroi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "enterprise.elroi.data.repository")
@EntityScan(basePackages = "enterprise.elroi.data.models")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}