package engineer.vel.projects.enrollmentsystem.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
/**
 * A service class to perform the logic for {@link CourseClass}.
 * 
 * @author Vadivel Murugesan
 *
 */
@Validated
public interface CourseClassService {

	/**
	 * 
	 * To add a {@link CourseClass}
	 * 
	 * @param courseClass
	 * @return
	 */
	CourseClassDto add(@Valid CourseClassDto courseClass);

	/**
	 * 
	 * Get the list of classes for a particular student for a semester, or the
	 * fully history of classes enrolled.
	 * 
	 * @param studentId
	 * @param semesterName
	 * @return
	 */
	List<CourseClassDto> fetchClasses(
			@Valid @NotNull(message = "{fetch.courseclass.id.notnull}") Long studentId,
			String semesterName);

	/**
	 * 
	 * Find the class by its name
	 * 
	 * @param name
	 * @return
	 */
	CourseClassDto findByName(
			@Valid @NotEmpty(message = "{find.courseclass.name.notempty}") String name);

}
