package engineer.vel.projects.enrollmentsystem.core.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import engineer.vel.projects.enrollmentsystem.repository.entity.Enrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * A Data Transformation Object for {@link Enrollment} requests to enroll a
 * student into a class.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EnrollmentRequestDto {

	@Schema(description = "Unique Identifier of the Student.", example = "1", required = true)
	@JsonProperty("id")
	@NotNull(message = "{request.enrollment.studentid.notnull}")
	private Long studentId;

	@Schema(description = "Name of the Semester.", example = "Spring2021", required = true)
	@NotEmpty(message = "{request.enrollment.semester.notempty}")
	private String semester;

	@Schema(description = "Name of the Class.", example = "classA", required = true)
	@JsonProperty("class")
	@NotEmpty(message = "{request.enrollment.courseclass.notempty}")
	private String courseClass;
}
