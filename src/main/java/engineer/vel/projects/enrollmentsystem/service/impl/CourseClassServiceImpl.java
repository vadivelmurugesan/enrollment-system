package engineer.vel.projects.enrollmentsystem.service.impl;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.core.exception.RecordNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.mapper.CourseClassMapper;
import engineer.vel.projects.enrollmentsystem.repository.CourseClassRepository;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
import engineer.vel.projects.enrollmentsystem.service.CourseClassService;
import lombok.extern.log4j.Log4j2;
/**
 * An implementation class for {@link CourseClassService}
 * 
 * @author Vadivel Murugesan
 *
 */
@Log4j2
@Service
public class CourseClassServiceImpl implements CourseClassService {

	@Autowired
	private CourseClassRepository courseClassRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseClassMapper courseClassMapper;

	@Override
	public List<CourseClassDto> fetchClasses(Long studentId,
			String semesterName) {
		log.info(
				"Fetching the classes enrolled for the student id - {} and semester name - {}",
				studentId, semesterName);
		List<CourseClass> fetchedClasses = studentRepository
				.fetchClasses(studentId, semesterName);
		return toDtos(fetchedClasses);
	}

	@Override
	@CachePut(value = "courseClassess", key = "#courseClass.name")
	public CourseClassDto add(CourseClassDto courseClass) {
		log.info("Saving the class - {} into the database",
				courseClass.getName());
		return toDto(courseClassRepository.save(toEntity(courseClass)));
	}

	@Override
	@Cacheable(value = "courseClasses", key = "#name", unless = "#result==null")
	public CourseClassDto findByName(String name) {
		log.info("Loading the class - {} from the database.", name);
		CourseClass courseClass = courseClassRepository.findByName(name)
				.orElseThrow(() -> new RecordNotFoundException(
						format("Class {0} not found.", name)));
		return toDto(courseClass);
	}

	private CourseClass toEntity(CourseClassDto courseClass) {
		return courseClassMapper.toEntity(courseClass);
	}

	private CourseClassDto toDto(CourseClass courseClass) {
		return courseClassMapper.toDto(courseClass);
	}

	private List<CourseClassDto> toDtos(List<CourseClass> entities) {
		return courseClassMapper.toDtos(entities);
	}

}
