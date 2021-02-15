package engineer.vel.projects.enrollmentsystem.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import engineer.vel.projects.enrollmentsystem.core.validation.CreationValidationGroup;
import engineer.vel.projects.enrollmentsystem.core.validation.UniqueStudentId;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Data Transformation Object for create/update the {@link Student} document.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentRequestDto {

	@Schema(description = "Unique Identifier of the Student.", example = "1", required = true)
	@NotNull(message = "{add.student.id.notnull}")
	@UniqueStudentId(groups = CreationValidationGroup.class)
	private Long id;

	@Schema(description = "First Name of the Student.", example = "Vadivel", required = true)
	@NotBlank(message = "{add.student.firstName.notempty}")
	private String firstName;

	@Schema(description = "Last Name of the Student.", example = "Murugesan", required = true)
	@NotBlank(message = "{add.student.lastName.notempty}")
	private String lastName;

	@Schema(description = "Nationality of the Student.", example = "US", required = false)
	private String nationality;
}
