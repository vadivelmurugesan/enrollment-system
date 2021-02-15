package engineer.vel.projects.enrollmentsystem.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.service.SemesterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Semester API
 * 
 * @author Vadivel Murugesan
 *
 */
@RestController
@Tag(name = "semester", description = "the Semester API")
public class SemesterController {

	@Autowired
	private SemesterService semesterService;

	/**
	 * API to add new semester
	 * 
	 * @param semester
	 * @return
	 */
	@Operation(summary = "Add a new semester", description = "Add the basic information of semester", tags = {
			"semester"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Semester created", content = @Content(schema = @Schema(implementation = SemesterDto.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input")})
	@PostMapping("/semesters")
	public ResponseEntity<Object> createSemester(
			@Parameter(description = "Semester to add. Cannot null or empty.", required = true, schema = @Schema(implementation = SemesterDto.class)) @RequestBody SemesterDto semester) {
		SemesterDto savedSemester = semesterService.add(semester);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{name}").buildAndExpand(savedSemester.getName())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	/**
	 * API to get semester by name.
	 * 
	 * @param id
	 * @return
	 */
	@Operation(summary = "Get semester by name", description = "Returns a single semester", tags = {
			"semester"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = SemesterDto.class))),
			@ApiResponse(responseCode = "404", description = "Semester not found")})
	@GetMapping("/semesters/{name}")
	public ResponseEntity<Object> findSemesterByName(
			@Parameter(description = "Name of the semester to be obtained. Cannot be empty.", required = true) @PathVariable String name) {

		SemesterDto semester = semesterService.findByName(name);

		return ResponseEntity.ok(semester);
	}
}
