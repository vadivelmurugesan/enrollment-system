package engineer.vel.projects.enrollmentsystem.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import engineer.vel.projects.enrollmentsystem.core.dto.SearchRequestDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentRequestDto;
import engineer.vel.projects.enrollmentsystem.core.validation.CreationValidationGroup;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;

/**
 * A service class to perform the logic for {@link Student}.
 * 
 * @author Vadivel Murugesan
 *
 */
@Validated
public interface StudentService {

	/**
	 * Update the information in the existing student record.
	 * 
	 * @param student
	 * @return
	 */
	StudentDto update(@Valid StudentRequestDto student);

	/**
	 * 
	 * Add a new student with the basic information.
	 * 
	 * @param student
	 * @return
	 */
	@Validated(CreationValidationGroup.class)
	StudentDto add(@Valid StudentRequestDto student);

	/**
	 * 
	 * Find Student by ID
	 * 
	 * @param id
	 * @return
	 */
	StudentDto findById(@Valid @NotNull(message = "Id is required.") Long id);

	/**
	 * 
	 * Get the list of students enrolled in a class for a particular semester.
	 * 
	 * @param searchRequest
	 * @return
	 */
	List<StudentDto> fetchStudents(@Valid SearchRequestDto searchRequest);

}
