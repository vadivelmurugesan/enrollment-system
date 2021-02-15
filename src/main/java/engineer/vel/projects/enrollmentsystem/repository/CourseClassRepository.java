package engineer.vel.projects.enrollmentsystem.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;

/**
 * 
 * {@link CourseClassRepository} to persist/fetch {@link CourseClass} collection
 * from mongodb.
 * 
 * @author Vadivel Murugesan
 *
 */
public interface CourseClassRepository
		extends
			MongoRepository<CourseClass, Integer> {

	/**
	 * Finds the {@link CourseClass} by its name.
	 * 
	 * @param name
	 * @return
	 */
	Optional<CourseClass> findByName(String name);

}
