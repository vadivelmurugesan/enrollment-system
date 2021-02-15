package engineer.vel.projects.enrollmentsystem.core.exception;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * This class provides centralized exception handling across all the rest
 * methods.
 * 
 * @author Vadivel Murugesan
 *
 */
@Log4j2
@RestControllerAdvice
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles if the request body is invalid or not readable.
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.error("Malformed JSON request", ex);
		return buildErrorResponse("Malformed JSON request",
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles if the method argument annotated with @Valid failed during the
	 * validation.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		log.error("Request has errors", ex);

		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setMessage("Request has errors");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		errorResponse.addValidationErrors(ex);
		return build(errorResponse);
	}

	/**
	 * 
	 * Handles if the value is missing in the request parameter.
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		log.error("Missing Parameters", ex);

		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setMessage("Missing Parameters");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		errorResponse.addValidationError(ex.getParameterName(), null,
				"parameter is missing");
		return build(errorResponse);
	}

	/**
	 * Handles if the requested method is not allowed
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.error("Method not allowed", ex);
		return buildErrorResponse("Method not allowed",
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Handles the ApiRequestException if any.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = ApiRequestException.class)
	public ResponseEntity<Object> handleApiRequestException(
			ApiRequestException ex) {
		log.error("Unable to process the request", ex);
		return ResponseEntity.badRequest().build();

	}

	/**
	 * Handles if the requested information is not available in the database.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Object> handleRecordNotFoundException(
			RecordNotFoundException ex) {
		log.error("Requested record not found", ex);
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles if the requested student information is not available in the
	 * database.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(StudentInfoNotFoundException.class)
	public ResponseEntity<Object> handleStudentInfoNotFoundException(
			StudentInfoNotFoundException ex) {
		log.error("Requested student record Not found", ex);
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception exception) {
		log.error("Error Occured: Unable to process your request at this time.",
				exception);
		return buildErrorResponse(
				"Error Occured: Unable to process your request at this time.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle if the method/class annotated with @Validated failed during the
	 * validation.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(
			ConstraintViolationException ex, WebRequest request) {

		log.error("Request has constraint violations", ex);

		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setMessage("Request has constraint violations");
		errorResponse.setStatus(HttpStatus.BAD_REQUEST);
		errorResponse.addValidationErrors(ex);
		return build(errorResponse);
	}

	/**
	 * Handle if the method argument type is mismatch
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		log.error("Type Mismatch", ex);
		return buildErrorResponse("Type Mismatch", HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception,
			HttpStatus httpStatus) {
		log.error(ExceptionUtils.getRootCauseMessage(exception), exception);
		return buildErrorResponse(exception.getMessage(), httpStatus);
	}

	private ResponseEntity<Object> buildErrorResponse(String message,
			HttpStatus httpStatus) {
		ApiErrorResponse errorResponse = new ApiErrorResponse();
		errorResponse.setMessage(message);
		errorResponse.setStatus(httpStatus);
		return build(errorResponse);
	}

	private ResponseEntity<Object> build(ApiErrorResponse errorResponse) {
		return ResponseEntity.status(errorResponse.getStatus())
				.body(errorResponse);
	}
}
