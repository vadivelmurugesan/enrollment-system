package engineer.vel.projects.enrollmentsystem.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import engineer.vel.projects.enrollmentsystem.repository.entity.Student;

/**
 * {@link StudentRepository} to persist/fetch {@link Student} collection from
 * mongodb.
 * 
 * @author Vadivel Murugesan
 *
 */
public interface StudentRepository
		extends
			MongoRepository<Student, Long>,
			StudentQueryRepository {

}
