package engineer.vel.projects.enrollmentsystem.repository.entity;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * A Document for the collection 'semesters'.
 * 
 * @author Vadivel Murugesan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Document(collection = "semesters")
public class Semester {
	@Id
	private ObjectId id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
}
