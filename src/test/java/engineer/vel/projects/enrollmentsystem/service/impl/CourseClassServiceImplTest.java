/**
 * 
 */
package engineer.vel.projects.enrollmentsystem.service.impl;

import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedCourseClass;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedCourseClassDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.core.exception.RecordNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.mapper.CourseClassMapperImpl;
import engineer.vel.projects.enrollmentsystem.repository.CourseClassRepository;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
import engineer.vel.projects.enrollmentsystem.service.CourseClassService;

/**
 * Tests all {@link CourseClassService} service methods.
 * 
 * @author Vadivel Murugesan
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CourseClassServiceImpl.class,
		CourseClassMapperImpl.class})
class CourseClassServiceImplTest {

	@Autowired
	private CourseClassService courseClassService;

	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private CourseClassRepository courseClassRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link engineer.vel.projects.enrollmentsystem.service.impl.CourseClassServiceImpl#fetchClasses(java.lang.Long, java.lang.String)}.
	 */
	@Test
	@DisplayName("Test fetch classes when the resulset is empty")
	final void testFetchClasses_withEmptyResult() {
		List<CourseClassDto> fetchedClasses = courseClassService
				.fetchClasses(1L, "Spring2021");

		assertTrue(fetchedClasses.isEmpty());

	}

	@Test
	@DisplayName("Test fetch classes when the resultset is not empty")
	final void testFetchClasses_withResult() {
		when(studentRepository.fetchClasses(anyLong(), anyString()))
				.thenReturn(mockedCourseClasses());

		List<CourseClassDto> fetchedClasses = courseClassService
				.fetchClasses(1L, "Spring2021");

		assertEquals(1, fetchedClasses.size());

	}

	private List<CourseClass> mockedCourseClasses() {
		return Stream.of(mockedCourseClass()).collect(Collectors.toList());
	}

	/**
	 * Test method for
	 * {@link engineer.vel.projects.enrollmentsystem.service.impl.CourseClassServiceImpl#add(engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto)}.
	 */
	@Test
	@DisplayName("add a class")
	final void testAdd() {

		when(courseClassRepository.save(any(CourseClass.class)))
				.then(i -> i.getArgument(0));

		CourseClassDto mockedCourseClassDto = mockedCourseClassDto();
		CourseClassDto addedClass = courseClassService
				.add(mockedCourseClassDto);

		assertNotNull(addedClass);
		assertEquals(mockedCourseClassDto.getName(), addedClass.getName());
	}

	/**
	 * Test method for
	 * {@link engineer.vel.projects.enrollmentsystem.service.impl.CourseClassServiceImpl#findByName(java.lang.String)}.
	 */
	@Test
	@DisplayName("find a class by its name but record not found")
	final void testFindByName_NotFound() {
		assertThrows(RecordNotFoundException.class, () -> {
			courseClassService.findByName("classA");
		});
	}

	@Test
	@DisplayName("find a class by its name")
	final void testFindByName() {
		when(courseClassRepository.findByName(anyString()))
				.thenReturn(Optional.of(mockedCourseClass()));

		String className = "classA";
		CourseClassDto fetchedClass = courseClassService.findByName(className);
		assertNotNull(fetchedClass);
		assertEquals(className, fetchedClass.getName());
	}
}
