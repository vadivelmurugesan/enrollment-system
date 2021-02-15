package engineer.vel.projects.enrollmentsystem.service.impl;

import static java.text.MessageFormat.format;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineer.vel.projects.enrollmentsystem.core.dto.SearchRequestDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentRequestDto;
import engineer.vel.projects.enrollmentsystem.core.exception.ApiRequestException;
import engineer.vel.projects.enrollmentsystem.core.exception.StudentInfoNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.mapper.StudentMapper;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import engineer.vel.projects.enrollmentsystem.service.StudentService;
import lombok.extern.log4j.Log4j2;

/**
 * An implementation class for {@link StudentService}.
 * 
 * @author Vadivel Murugesan
 *
 */
@Log4j2
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentMapper studentMapper;

	@Override
	public StudentDto add(StudentRequestDto student) {
		if (isExistsById(student)) {
			log.error("Student id {} already exists", student.getId());
			throw new ApiRequestException(
					format("Student id {0} already exists", student.getId()));
		}
		log.info("Saving the student - {} into the database", student.getId());
		return toDto(studentRepository.save(toEntity(student)));

	}

	@Override
	public StudentDto update(StudentRequestDto student) {
		if (isExistsById(student)) {
			log.info("Updating the student - {} into the database",
					student.getId());
			return toDto(studentRepository.save(toEntity(student)));
		} else {
			log.error("Student id {} not found", student.getId());
			throw new StudentInfoNotFoundException(student.getId());
		}
	}

	@Override
	public List<StudentDto> fetchStudents(SearchRequestDto searchRequest) {
		log.info("Loading the students from the database.");
		return studentMapper
				.toDtos(studentRepository.fetchStudents(searchRequest));
	}

	@Override
	public StudentDto findById(Long id) {
		Student fetchedStudent = findOrElesThrowEx(id);
		return toDto(fetchedStudent);

	}

	private Student toEntity(StudentRequestDto student) {
		return studentMapper.toEntity(student);
	}

	private StudentDto toDto(Student entity) {
		return studentMapper.toDto(entity);
	}

	private boolean isExistsById(StudentRequestDto student) {
		return studentRepository.existsById(student.getId());
	}

	private Student findOrElesThrowEx(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new StudentInfoNotFoundException(id));
	}
}
