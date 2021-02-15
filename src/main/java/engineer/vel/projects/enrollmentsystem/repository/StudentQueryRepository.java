package engineer.vel.projects.enrollmentsystem.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import engineer.vel.projects.enrollmentsystem.core.dto.SearchRequestDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;

/**
 * 
 * {@link StudentQueryRepository} is an interface to fetch the records by using
 * {@link MongoOperations} and {@link Aggregation}
 * 
 * @author Vadivel Murugesan
 *
 */
public interface StudentQueryRepository {

	/**
	 * Gets the list of students enrolled in a class for a particular semester.
	 * 
	 * @param searchRequest
	 * @return
	 */
	List<Student> fetchStudents(SearchRequestDto searchRequest);

	/**
	 * 
	 * Gets the list of classes for a particular student for a semester, or the
	 * fully history of classes enrolled.
	 * 
	 * @param studentId
	 * @param semesterName
	 * @return
	 */
	List<CourseClass> fetchClasses(Long studentId, String semesterName);

	/**
	 * Helps to drop a student from a class.
	 * 
	 * @param id
	 * @param courseClass
	 * @return
	 */
	boolean disenroll(Long id, String courseClass);

}
