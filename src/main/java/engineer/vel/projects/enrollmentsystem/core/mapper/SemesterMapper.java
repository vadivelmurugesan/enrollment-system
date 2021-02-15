package engineer.vel.projects.enrollmentsystem.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.Semester;
/**
 * This class is a map struct class to map the object from {@link Semester}
 * to {@link SemesterDto} vice versa.
 * 
 * @author Vadivel Murugesan
 *
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SemesterMapper {

	Semester toEntity(SemesterDto dto);

	SemesterDto toDto(Semester entity);

}
