package engineer.vel.projects.enrollmentsystem.core.dto;

import static engineer.vel.projects.enrollmentsystem.core.constant.AppConstants.APP_DATE_FORMAT;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import engineer.vel.projects.enrollmentsystem.repository.entity.Semester;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Data Transformation Object for {@link Semester} entity.
 * 
 * @author Vadivel Murugesan
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SemesterDto {

	@Schema(description = "Name of the Semester.", example = "Spring2021", required = true)
	@NotNull(message = "{add.semester.name.notempty}")
	private String name;

	@Schema(description = "Start Date of the Semester.", example = "2021-03-14", required = false)
	@JsonFormat(pattern = APP_DATE_FORMAT)
	private LocalDate startDate;

	@Schema(description = "End Date of the Semester.", example = "2021-06-14", required = false)
	@JsonFormat(pattern = APP_DATE_FORMAT)
	private LocalDate endDate;
}
