package engineer.vel.projects.enrollmentsystem.core.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
/**
 * 
 * This class helps to structurize the response during the exception.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
	private HttpStatus status;
	private String message;
	private List<ApiValidationError> errors;

	public void addValidationError(String field, Object rejectedValue,
			String message) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		errors.add(new ApiValidationError(field, rejectedValue, message));
	}

	public void addValidationErrors(MethodArgumentNotValidException ex) {
		ex.getBindingResult().getFieldErrors().stream()
				.forEach(error -> addValidationError(error.getField(),
						error.getRejectedValue(), error.getDefaultMessage()));

	}

	public void addValidationErrors(ConstraintViolationException ex) {
		ex.getConstraintViolations().stream()
				.forEach(error -> addValidationError(
						error.getPropertyPath().toString(),
						error.getInvalidValue(), error.getMessage()));

	}

}