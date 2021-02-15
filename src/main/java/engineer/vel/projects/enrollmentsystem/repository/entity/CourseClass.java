package engineer.vel.projects.enrollmentsystem.repository.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A Document for the collection 'classes'.
 * 
 * @author Vadivel Murugesan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Document(collection = "classes")
public class CourseClass {
	@Id
	private ObjectId id;

	private String name;

	private Integer credit;
}
