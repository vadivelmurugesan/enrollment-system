package engineer.vel.projects.enrollmentsystem.repository.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * An embedded document reference for Enrollment.
 * 
 * @author Vadivel Murugesan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Enrollment {
	private String semester;
	private Set<String> classes;
	private Boolean isFullTime;

	public void addCourseClass(String courseClass) {
		if (classes == null) {
			classes = new HashSet<>();
		}
		classes.add(courseClass);
	}
}
