package engineer.vel.projects.enrollmentsystem;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
 * This class requires for the WAR deployment, which binds {@link Servlet},
 * {@link Filter} and {@link ServletContextInitializer} beans from the
 * application context to the server.
 * 
 * @author Vadivel Murugesan
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(EnrollmentSystemApplication.class);
	}

}
