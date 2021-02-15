package engineer.vel.projects.enrollmentsystem.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A custom exception for enrollment limitation per semester.
 * 
 * @author Vadivel Murugesan
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidEnrollmentRequestException extends RuntimeException {

	private static final long serialVersionUID = -6702055005654941371L;

	public InvalidEnrollmentRequestException() {
		super("Each student is only allowed to be enrolled in a maximum of 20 credits of each semester.");
	}
}