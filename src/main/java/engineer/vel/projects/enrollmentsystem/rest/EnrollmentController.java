package engineer.vel.projects.enrollmentsystem.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto;
import engineer.vel.projects.enrollmentsystem.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Enrollment API
 * 
 * @author Vadivel Murugesan
 *
 */
@RestController
@Tag(name = "enrollment", description = "the Enrollment API")
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	/**
	 * API to enroll a student into a class for a particular semester
	 * 
	 * @param enrollment
	 * @return
	 */
	@Operation(summary = "Enroll a student into a class", description = "Enroll a student into a class for a particular semester", tags = {
			"enrollment"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = EnrollmentRequestDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "404", description = "Student not found")})
	@PostMapping("/enrollments")
	public ResponseEntity<Object> enroll(
			@Parameter(description = "Enrollment Request. Cannot null or empty.", required = true, schema = @Schema(implementation = EnrollmentRequestDto.class)) @RequestBody EnrollmentRequestDto enrollmentRequest) {
		enrollmentService.enroll(enrollmentRequest);

		return ResponseEntity.ok().build();

	}

	/**
	 * API to drop a student from a class.
	 * 
	 * @param id
	 */
	@Operation(summary = "Drop a student from a class", description = "", tags = {
			"contact"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "404", description = "Student not found")})
	@DeleteMapping("/enrollments")
	public ResponseEntity<Object> disenroll(
			@Parameter(description = "Class name", required = true) @RequestParam(name = "class", required = true) String courseClass,
			@Parameter(description = "Student id", required = true) @RequestParam(required = true) Long id) {
		enrollmentService.disenroll(id, courseClass);
		return ResponseEntity.ok().build();
	}
}
