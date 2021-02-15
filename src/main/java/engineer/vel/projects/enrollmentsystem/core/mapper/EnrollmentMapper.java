package engineer.vel.projects.enrollmentsystem.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.Enrollment;
/**
 * This class is a map struct class to map the object from {@link Enrollment} to
 * {@link EnrollmentDto} vice versa.
 * 
 * @author Vadivel Murugesan
 *
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnrollmentMapper {

	Enrollment toEntity(EnrollmentDto dto);

	EnrollmentDto toDto(Enrollment entity);

}