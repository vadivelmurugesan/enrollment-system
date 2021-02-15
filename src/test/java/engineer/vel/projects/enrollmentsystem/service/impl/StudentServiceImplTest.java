package engineer.vel.projects.enrollmentsystem.service.impl;

import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSearchRequestDto;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedStudent;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedStudentRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import engineer.vel.projects.enrollmentsystem.core.dto.StudentDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentRequestDto;
import engineer.vel.projects.enrollmentsystem.core.exception.StudentInfoNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.mapper.EnrollmentMapperImpl;
import engineer.vel.projects.enrollmentsystem.core.mapper.StudentMapperImpl;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import engineer.vel.projects.enrollmentsystem.service.StudentService;

/**
 * Tests all {@link StudentService} service methods.
 * 
 * @author Vadivel Murugesan
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StudentServiceImpl.class,
		StudentMapperImpl.class, EnrollmentMapperImpl.class})
class StudentServiceImplTest {

	@Autowired
	private StudentService studentService;

	@MockBean
	private StudentRepository studentRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test fetch students when the result set is empty")
	final void testFetchStudents_withEmptyResult() {
		List<StudentDto> fetchedStudents = studentService
				.fetchStudents(mockedSearchRequestDto());

		assertTrue(fetchedStudents.isEmpty());

	}

	@Test
	@DisplayName("Test fetch classes when the resultset is not empty")
	final void testFetchStudents_withResult() {
		when(studentRepository.fetchStudents(mockedSearchRequestDto()))
				.thenReturn(mockedStudents());

		List<StudentDto> fetchedStudents = studentService
				.fetchStudents(mockedSearchRequestDto());

		assertEquals(1, fetchedStudents.size());

	}

	private List<Student> mockedStudents() {
		return Stream.of(mockedStudent()).collect(Collectors.toList());
	}

	@Test
	@DisplayName("Add a student")
	final void testAdd() {

		when(studentRepository.save(any(Student.class)))
				.then(i -> i.getArgument(0));

		StudentRequestDto mockedStudentRequest = mockedStudentRequest();
		StudentDto addedStudent = studentService.add(mockedStudentRequest);

		assertNotNull(addedStudent);
		assertEquals(mockedStudentRequest.getFirstName(),
				addedStudent.getFirstName());
		assertEquals(mockedStudentRequest.getLastName(),
				addedStudent.getLastName());
		assertEquals(mockedStudentRequest.getId(), addedStudent.getId());
		assertEquals(mockedStudentRequest.getNationality(),
				addedStudent.getNationality());
	}

	@Test
	@DisplayName("Update student")
	final void testUpdate() {

		when(studentRepository.existsById(eq(1L))).thenReturn(true);
		when(studentRepository.save(any(Student.class)))
				.then(i -> i.getArgument(0));

		StudentRequestDto mockedStudentRequest = mockedStudentRequest();
		mockedStudentRequest.setFirstName("UpdatedName");
		StudentDto updatedStudent = studentService.update(mockedStudentRequest);

		assertNotNull(updatedStudent);
		assertEquals(mockedStudentRequest.getFirstName(),
				updatedStudent.getFirstName());
	}

	@Test
	@DisplayName("Update student when id not found")
	final void testUpdate_whenIdNotFound() {

		when(studentRepository.existsById(eq(1L))).thenReturn(false);

		StudentRequestDto mockedStudentRequest = mockedStudentRequest();
		mockedStudentRequest.setFirstName("UpdatedName");

		assertThrows(StudentInfoNotFoundException.class,
				() -> studentService.update(mockedStudentRequest));

	}

	@Test
	@DisplayName("Find a student by its id but student info not found")
	final void testFindById_NotFound() {
		assertThrows(StudentInfoNotFoundException.class, () -> {
			studentService.findById(1L);
		});
	}

	@Test
	@DisplayName("Find a student by its id")
	final void testFindById() {
		when(studentRepository.findById(anyLong()))
				.thenReturn(Optional.of(mockedStudent()));

		StudentDto fetchedStudent = studentService.findById(1L);
		assertNotNull(fetchedStudent);
		assertEquals(1L, fetchedStudent.getId());
	}

}
