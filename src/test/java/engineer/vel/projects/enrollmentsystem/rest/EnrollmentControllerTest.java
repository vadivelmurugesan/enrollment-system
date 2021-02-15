/**
 * 
 */
package engineer.vel.projects.enrollmentsystem.rest;

import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertBadRequest;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertNotFound;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertOK;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockCourseClass;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedCourseClass;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedEnrollmentRequestDto;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemester;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedStudent;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto;
import engineer.vel.projects.enrollmentsystem.repository.CourseClassRepository;
import engineer.vel.projects.enrollmentsystem.repository.SemesterRepository;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import engineer.vel.projects.enrollmentsystem.test.IntegrationTestBase;
import engineer.vel.projects.enrollmentsystem.test.NullableArgumentConverter;

/**
 * Tests all {@link EnrollmentController} APIs.
 * 
 * @author Vadivel Murugesan
 *
 */
class EnrollmentControllerTest extends IntegrationTestBase {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseClassRepository courseClassRepository;

	@Autowired
	private SemesterRepository semesterRepository;

	private static final String BASE_CONTEXT_PATH = "/enrollments";

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		// Needs to clear the embedded database after each method to make the
		// junit
		// methods work independently.
		studentRepository.deleteAll();
		courseClassRepository.deleteAll();
		semesterRepository.deleteAll();
	}

	@Nested
	@DisplayName("Test API to enroll a student into a class for a particular semester")
	class Enroll {

		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.EnrollmentController#enroll(engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto)}.
		 */
		@Test
		@DisplayName("When no content in the body")
		final void testEnroll_withoutBody() throws Exception {
			ResultActions resultActions = mockMvc
					.perform(post(BASE_CONTEXT_PATH));

			assertBadRequest("Malformed JSON request", resultActions);
		}

		@Test
		@DisplayName("When the values are null in the request")
		final void testEnroll_withNullValues() throws Exception {

			EnrollmentRequestDto request = new EnrollmentRequestDto();
			ResultActions resultActions = performPost(BASE_CONTEXT_PATH,
					request);

			assertBadRequest("Request has constraint violations", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(3)))
					.andExpect(jsonPath(
							"$.errors[?(@.field == 'enroll.enrollmentRequest.studentId')].message",
							hasItem("Id is required.")))
					.andExpect(jsonPath(
							"$.errors[?(@.field == 'enroll.enrollmentRequest.semester')].message",
							hasItem("Semester is required.")))
					.andExpect(jsonPath(
							"$.errors[?(@.field == 'enroll.enrollmentRequest.courseClass')].message",
							hasItem("Class is required.")));
		}

		@Test
		@DisplayName("When the input is valid but the id is not found in the database")
		final void testEnroll_withValidInputNIdNotFound() throws Exception {

			ResultActions resultActions = performPost(BASE_CONTEXT_PATH,
					mockedEnrollmentRequestDto());

			assertNotFound("Student id 1 not found.", resultActions);

		}

		@Test
		@DisplayName("When the input is valid")
		final void testEnroll_withValidInputNIdFound() throws Exception {

			semesterRepository.save(mockedSemester());
			courseClassRepository.save(mockedCourseClass());
			studentRepository.save(mockedStudent());

			courseClassRepository.save(mockCourseClass("classB"));

			ResultActions resultActions = performPost(BASE_CONTEXT_PATH,
					mockedEnrollmentRequestDto());

			assertOK(resultActions);

			Optional<Student> fetchedStudent = studentRepository.findById(1L);

			Assertions.assertTrue(fetchedStudent.isPresent());

			Assertions.assertEquals(2, fetchedStudent.get().getEnrollments()
					.get(0).getClasses().size());
		}
	}

	@Nested
	@DisplayName("Test API to drop a student from a class.")
	class Disenroll {
		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.EnrollmentController#disenroll(java.lang.String, java.lang.Long)}.
		 */
		@Test
		@DisplayName("When missing the parameters in the request")
		final void testDisenroll_missingParameters() throws Exception {
			ResultActions resultActions = mockMvc
					.perform(delete(BASE_CONTEXT_PATH));

			assertBadRequest("Missing Parameters", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors[0].field", is("class")))
					.andExpect(
							jsonPath("$.errors[0].rejectedValue", nullValue()))
					.andExpect(jsonPath("$.errors[0].message",
							is("parameter is missing")));
		}

		@Test
		@DisplayName("When type is mismatch")
		final void testDisenroll_typeMismatch() throws Exception {
			ResultActions resultActions = mockMvc.perform(
					delete(BASE_CONTEXT_PATH).param("id", "invalidData")
							.param("class", "classA"));

			assertBadRequest("Type Mismatch", resultActions);
		}

		@ParameterizedTest
		@CsvSource({"123, null", "null, classA"})
		@DisplayName("When the input is valid")
		final void testDisenroll_invalidParameters(
				@ConvertWith(NullableArgumentConverter.class) String id,
				@ConvertWith(NullableArgumentConverter.class) String courseClass)
				throws Exception {

			ResultActions resultActions = mockMvc
					.perform(delete(BASE_CONTEXT_PATH).param("id", id)
							.param("class", courseClass));

			assertBadRequest("Missing Parameters", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(
							jsonPath("$.errors[0].rejectedValue", nullValue()))
					.andExpect(jsonPath("$.errors[0].message",
							is("parameter is missing")));
		}

		@Test
		@DisplayName("When input is valid")
		final void testDisenroll_validInput() throws Exception {

			studentRepository.save(mockedStudent());

			ResultActions resultActions = mockMvc
					.perform(delete(BASE_CONTEXT_PATH).param("id", "1")
							.param("class", "classA"));

			assertOK(resultActions);

			Optional<Student> fetchedStudent = studentRepository.findById(1L);

			Assertions.assertTrue(fetchedStudent.isPresent());

			Assertions.assertTrue(fetchedStudent.get().getEnrollments().get(0)
					.getClasses().isEmpty());
		}
	}

}
