package engineer.vel.projects.enrollmentsystem.repository.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A Document for the collection 'students'.
 * 
 * @author Vadivel Murugesan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Document(collection = "students")
public class Student {
	private Long id;
	private String firstName;
	private String lastName;
	private String nationality;
	private List<Enrollment> enrollments;

	public void addEnrollment(Enrollment enrollment) {
		if (enrollments == null) {
			enrollments = new ArrayList<>();
		}
		enrollments.add(enrollment);
	}

}
