package engineer.vel.projects.enrollmentsystem.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import engineer.vel.projects.enrollmentsystem.repository.entity.Semester;

/**
 * {@link SemesterRepository} to persist/fetch {@link Semester} collection from
 * mongodb.
 * 
 * @author Vadivel Murugesan
 *
 */
public interface SemesterRepository extends MongoRepository<Semester, Integer> {

	/**
	 * Find {@link Semester} by its name.
	 * 
	 * @param name
	 * @return
	 */
	Optional<Semester> findByName(String name);
}
