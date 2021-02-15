package engineer.vel.projects.enrollmentsystem.core.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
/**
 * This class is a map struct class to map the object from {@link CourseClass}
 * to {@link CourseClassDto} vice versa.
 * 
 * @author Vadivel Murugesan
 *
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseClassMapper {

	CourseClass toEntity(CourseClassDto dto);

	CourseClassDto toDto(CourseClass entity);

	List<CourseClassDto> toDtos(List<CourseClass> entities);

}