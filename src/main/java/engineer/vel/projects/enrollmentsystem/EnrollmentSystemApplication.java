package engineer.vel.projects.enrollmentsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
/**
 * 
 * This class is the heart of the application, which triggers the configuration
 * and scan the dependencies.
 * 
 * @author Vadivel Murugesan
 *
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = {
		"engineer.vel.projects.enrollmentsystem.repository"})
public class EnrollmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentSystemApplication.class, args);
	}

}
