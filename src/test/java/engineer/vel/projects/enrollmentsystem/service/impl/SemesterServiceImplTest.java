package engineer.vel.projects.enrollmentsystem.service.impl;

import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemester;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemesterDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.core.exception.RecordNotFoundException;
import engineer.vel.projects.enrollmentsystem.core.mapper.SemesterMapperImpl;
import engineer.vel.projects.enrollmentsystem.repository.SemesterRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.Semester;
import engineer.vel.projects.enrollmentsystem.service.SemesterService;

/**
 * Tests all {@link SemesterService} service methods.
 * 
 * @author Vadivel Murugesan
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SemesterServiceImpl.class,
		SemesterMapperImpl.class})
class SemesterServiceImplTest {

	@Autowired
	private SemesterService semesterService;

	@MockBean
	private SemesterRepository semesterRepository;

	@Test
	@DisplayName("Add a semester")
	final void testAdd() {

		when(semesterRepository.save(any(Semester.class)))
				.then(i -> i.getArgument(0));

		SemesterDto mockedSemesterDto = mockedSemesterDto();
		SemesterDto addedSemester = semesterService.add(mockedSemesterDto);

		assertNotNull(addedSemester);
		assertEquals(mockedSemesterDto.getName(), addedSemester.getName());
	}

	@Test
	@DisplayName("Find a semester by its name but record not found")
	final void testFindByName_NotFound() {
		assertThrows(RecordNotFoundException.class, () -> {
			semesterService.findByName("classA");
		});
	}

	@Test
	@DisplayName("Find a semester by its name")
	final void testFindByName() {
		when(semesterRepository.findByName(anyString()))
				.thenReturn(Optional.of(mockedSemester()));

		String semester = "Spring2021";
		SemesterDto fetchedClass = semesterService.findByName(semester);
		assertNotNull(fetchedClass);
		assertEquals(semester, fetchedClass.getName());
	}

}
