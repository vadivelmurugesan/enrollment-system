package engineer.vel.projects.enrollmentsystem.core.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Data Transformation Object for {@link CourseClass}.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CourseClassDto {

	@Schema(description = "Name of the Class.", example = "classA", required = true)
	@NotEmpty(message = "{add.courseclass.name.notempty}")
	private String name;

	@Schema(description = "Fixed Credit/Unit of the Class. Some harder classes be 4 credits while easier one could 2 or 3 credits.", example = "4", required = true)
	@NotNull(message = "{add.courseclass.credit.notnull}")
	@Max(value = 4, message = "{add.courseclass.credit.max}")
	private Integer credit;

}
