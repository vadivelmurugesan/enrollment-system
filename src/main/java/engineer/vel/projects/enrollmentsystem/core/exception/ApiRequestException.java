package engineer.vel.projects.enrollmentsystem.core.exception;

/**
 * The type {@link ApiRequestException} is a custom exception for this
 * application.
 */
public class ApiRequestException extends RuntimeException {

	private static final long serialVersionUID = 4048997951057125742L;

	/**
	 * Instantiates a new ApiRequestException
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ApiRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiRequestException(String message) {
		super(message);
	}
}
