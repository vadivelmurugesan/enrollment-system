package engineer.vel.projects.enrollmentsystem.service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto;

/**
 * A service class to perform the logic for the enrollments.
 * 
 * @author Vadivel Murugesan
 *
 */
@Validated
public interface EnrollmentService {

	/**
	 * 
	 * Enroll a student to a class for a particular semester
	 * 
	 * @param enrollmentRequest
	 */
	void enroll(@Valid EnrollmentRequestDto enrollmentRequest);

	/**
	 * Drop a student from a class.
	 * 
	 * @param id
	 * @param courseClass
	 */
	void disenroll(
			@Valid @NotNull(message = "{request.enrollment.studentid.notnull}") Long id,
			@NotEmpty(message = "{request.enrollment.courseclass.notempty}") String courseClass);
}
