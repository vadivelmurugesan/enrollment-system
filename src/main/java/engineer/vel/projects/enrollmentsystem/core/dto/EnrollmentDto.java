package engineer.vel.projects.enrollmentsystem.core.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import engineer.vel.projects.enrollmentsystem.repository.entity.Enrollment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Data Transformation Object for {@link Enrollment}
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EnrollmentDto {

	private String semester;
	private Set<String> classes;

	@JsonInclude(Include.NON_NULL)
	private Boolean isFullTime;
}
