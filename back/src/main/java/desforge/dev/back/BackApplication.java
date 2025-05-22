package desforge.dev.back;

import desforge.dev.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "desforge.dev.controllers, desforge.dev.security," +
        "desforge.dev.services, desforge.dev.modals" )
@EnableJpaRepositories(basePackages = "desforge.dev.repositories")
@EntityScan(basePackages = "desforge.dev.entities")
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

}
