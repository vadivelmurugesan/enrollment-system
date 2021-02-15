package engineer.vel.projects.enrollmentsystem.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import engineer.vel.projects.enrollmentsystem.core.dto.StudentDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentRequestDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;

/**
 * This class is a map struct class to map the object from {@link Student} to
 * {@link StudentDto} vice versa.
 * 
 * @author Vadivel Murugesan
 *
 */
@Mapper(componentModel = "spring", uses = {
		EnrollmentMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

	StudentDto toDto(Student entity);

	List<StudentDto> toDtos(List<Student> entities);

	Student toEntity(StudentRequestDto student);

}
