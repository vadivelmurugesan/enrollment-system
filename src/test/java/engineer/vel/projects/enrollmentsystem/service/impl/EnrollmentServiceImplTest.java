package engineer.vel.projects.enrollmentsystem.service.impl;

import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockCourseClass;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedCourseClass;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedEnrollmentRequestDto;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemester;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedStudent;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto;
import engineer.vel.projects.enrollmentsystem.core.exception.RecordNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.exception.StudentInfoNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.mapper.CourseClassMapperImpl;
import engineer.vel.projects.enrollmentsystem.core.mapper.SemesterMapperImpl;
import engineer.vel.projects.enrollmentsystem.repository.CourseClassRepository;
import engineer.vel.projects.enrollmentsystem.repository.SemesterRepository;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import engineer.vel.projects.enrollmentsystem.service.EnrollmentService;

/**
 * Tests all {@link EnrollmentService} service methods.
 * 
 * @author Vadivel Murugesan
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {EnrollmentServiceImpl.class,
		SemesterServiceImpl.class, CourseClassServiceImpl.class,
		CourseClassMapperImpl.class, SemesterMapperImpl.class})
class EnrollmentServiceImplTest {

	@Autowired
	private EnrollmentService enrollmentService;

	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private CourseClassRepository courseClassRepository;

	@MockBean
	private SemesterRepository semesterRepository;

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
	@DisplayName("Enroll a class when student id not found")
	final void testEnroll_whenStudentIdNotFound() {
		EnrollmentRequestDto mockedRequest = mockedEnrollmentRequestDto();
		assertThrows(StudentInfoNotFoundException.class,
				() -> enrollmentService.enroll(mockedRequest));
	}

	@Test
	@DisplayName("Enroll a class when class name not found")
	final void testEnroll_whenClassNameNotFound() {

		when(studentRepository.findById(eq(1L)))
				.thenReturn(Optional.of(mockedStudent()));
		EnrollmentRequestDto mockedRequest = mockedEnrollmentRequestDto();

		assertThrows(RecordNotFoundException.class,
				() -> enrollmentService.enroll(mockedRequest));
	}

	@Test
	@DisplayName("Enroll a class when semester name not found")
	final void testEnroll_whenSemesterNameNotFound() {

		when(studentRepository.findById(eq(1L)))
				.thenReturn(Optional.of(mockedStudent()));
		when(semesterRepository.findByName(eq("Spring2021")))
				.thenReturn(Optional.of(mockedSemester()));

		EnrollmentRequestDto mockedRequest = mockedEnrollmentRequestDto();

		assertThrows(RecordNotFoundException.class,
				() -> enrollmentService.enroll(mockedRequest));
	}

	@Test
	@DisplayName("Enroll a class")
	final void testEnroll() {

		when(studentRepository.findById(eq(1L)))
				.thenReturn(Optional.of(mockedStudent()));

		when(semesterRepository.findByName(eq("Spring2021")))
				.thenReturn(Optional.of(mockedSemester()));

		when(courseClassRepository.findByName(eq("classB")))
				.thenReturn(Optional.of(mockCourseClass("classB")));
		when(courseClassRepository.findByName(eq("classA")))
				.thenReturn(Optional.of(mockedCourseClass()));

		enrollmentService.enroll(mockedEnrollmentRequestDto());

		verify(studentRepository, times(1)).save(any());

	}

	@Test
	@DisplayName("Enroll a class with no prior enrollments")
	final void testEnroll_WithNoPriorEnrollments() {

		Student mockedStudent = mockedStudent();
		mockedStudent.setEnrollments(null);
		when(studentRepository.findById(eq(1L)))
				.thenReturn(Optional.of(mockedStudent));
		when(semesterRepository.findByName(eq("Spring2021")))
				.thenReturn(Optional.of(mockedSemester()));
		when(courseClassRepository.findByName(eq("classB")))
				.thenReturn(Optional.of(mockCourseClass("classB")));

		enrollmentService.enroll(mockedEnrollmentRequestDto());

		verify(studentRepository, times(1)).save(any());

	}

}
