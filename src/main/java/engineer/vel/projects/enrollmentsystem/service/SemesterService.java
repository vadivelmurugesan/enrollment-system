package engineer.vel.projects.enrollmentsystem.service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.Semester;
/**
 * A service class to perform the logic for {@link Semester}.
 * 
 * 
 * @author Vadivel Murugesan
 *
 */
@Validated
public interface SemesterService {

	/**
	 * Add a semester
	 * 
	 * @param semester
	 * @return
	 */
	SemesterDto add(@Valid SemesterDto semester);

	/**
	 * Find a semester by its name
	 * 
	 * @param name
	 * @return
	 */
	SemesterDto findByName(
			@Valid @NotEmpty(message = "{find.semester.name.notempty}") String name);

}
