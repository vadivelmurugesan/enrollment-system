package engineer.vel.projects.enrollmentsystem.core.dto;

import java.util.List;

import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * A Data Transformation Object for {@link Student} entity.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String nationality;

	private List<EnrollmentDto> enrollments;
}
