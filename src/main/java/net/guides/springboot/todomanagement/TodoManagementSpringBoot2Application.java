package net.guides.springboot.todomanagement;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import net.guides.springboot.todomanagement.model.User;
import net.guides.springboot.todomanagement.service.UserService;

@SpringBootApplication
public class TodoManagementSpringBoot2Application {

	public static void main(String[] args) {
		SpringApplication.run(TodoManagementSpringBoot2Application.class, args);
	}

	@Bean
	public ApplicationRunner initializer(UserService userService) {
		return args -> {
			if (userService.findByUsername("admin") == null) {
				User admin = new User("admin", "admin", "ADMIN");
				userService.saveUser(admin);
				System.out.println("Created admin user with username=admin password=admin");
			}
		};
	}
}