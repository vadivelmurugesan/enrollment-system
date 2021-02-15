package engineer.vel.projects.enrollmentsystem.core.exception;

import static java.text.MessageFormat.format;

/**
 * This class is a custom exception for a student not found in the database.
 * 
 * @author Vadivel Murugesan
 *
 */
public class StudentInfoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -159264386600812023L;

	public StudentInfoNotFoundException(Long studentId) {
		super(format("Student id {0} not found.", studentId));
	}
}
