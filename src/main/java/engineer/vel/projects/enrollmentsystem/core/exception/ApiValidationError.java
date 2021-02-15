package engineer.vel.projects.enrollmentsystem.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A Class for holding the validation errors.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiValidationError {
	private String field;
	private Object rejectedValue;
	private String message;
}
