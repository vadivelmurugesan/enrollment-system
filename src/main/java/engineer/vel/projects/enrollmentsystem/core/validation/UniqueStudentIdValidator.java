package engineer.vel.projects.enrollmentsystem.core.validation;

import static java.text.MessageFormat.format;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * {@link UniqueStudentIdValidator}Fs defines the logic to validate a given student id is unique.
 * 
 * @author Vadivel Murugesan
 *
 */
@Log4j2
public class UniqueStudentIdValidator
		implements
			ConstraintValidator<UniqueStudentId, Long> {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {

		// Checks the id already exists in the database. If it does, then it is
		// invalid.
		if (value != null && studentRepository.existsById(value)) {
			log.error(format("Student id {0} already exists", value));
			return false;
		}
		return true;
	}

}