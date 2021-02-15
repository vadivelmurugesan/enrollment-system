/**
 * 
 */
package engineer.vel.projects.enrollmentsystem.rest;

import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertBadRequest;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertCreated;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertMethodNotAllowed;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertNotFound;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertOK;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedCourseClass;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemester;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedStudent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.repository.CourseClassRepository;
import engineer.vel.projects.enrollmentsystem.repository.SemesterRepository;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.test.IntegrationTestBase;

/**
 * Tests all {@link CourseClassController} APIs.
 * 
 * @author Vadivel Murugesan
 *
 */
class CourseClassControllerIntTest extends IntegrationTestBase {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseClassRepository courseClassRepository;

	@Autowired
	private SemesterRepository semesterRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		courseClassRepository.deleteAll();
	}

	@Nested
	@DisplayName("Test the API to get the list of classes")
	class FetchClass {

		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.CourseClassController#fetchClasses(java.lang.String, java.lang.Long)}.
		 * 
		 * @throws Exception
		 */
		@Test
		@DisplayName("When missing the parameters in the request")
		final void testFetchClassesWhenMissingParameters() throws Exception {
			ResultActions resultActions = mockMvc.perform(get("/fetchClasses"));

			assertBadRequest("Missing Parameters", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors[0].field", is("id")))
					.andExpect(
							jsonPath("$.errors[0].rejectedValue", nullValue()))
					.andExpect(jsonPath("$.errors[0].message",
							is("parameter is missing")));
		}

		@Test
		@DisplayName("When type is mismatch")
		final void testFetchClassesWhenTypeMismatch() throws Exception {
			ResultActions resultActions = mockMvc
					.perform(get("/fetchClasses").param("id", "invalidData"));

			assertBadRequest("Type Mismatch", resultActions);
		}

		@ParameterizedTest
		@CsvSource({"123, null", "123, Spring2021"})
		@DisplayName("When the input is valid")
		final void testFetchClasses_ByStudentIdNSemesterWithEmptyResult(
				String id, String semester) throws Exception {

			semesterRepository.save(mockedSemester());
			courseClassRepository.save(mockedCourseClass());
			studentRepository.save(mockedStudent());

			ResultActions resultActions = mockMvc.perform(get("/fetchClasses")
					.param("id", id).param("semester", semester));

			assertOK(resultActions).andExpect(jsonPath("$").isArray());
		}

	}

	@Nested
	@DisplayName("Test API to add new class")
	class CreateClass {

		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.CourseClassController#createClass(engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto)}.
		 * 
		 * @throws Exception
		 */
		@Test
		@DisplayName("When no content in the body")
		final void testCreateClass_withoutBody() throws Exception {
			ResultActions resultActions = mockMvc.perform(post("/classes"));

			assertBadRequest("Malformed JSON request", resultActions);
		}

		@Test
		@DisplayName("When the class name is null")
		final void testCreateClass_withNullName() throws Exception {

			CourseClassDto courseClass = new CourseClassDto();
			courseClass.setName(null);
			courseClass.setCredit(3);

			ResultActions resultActions = performPost("/classes", courseClass);

			assertBadRequest("Request has constraint violations", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors[0].field",
							is("add.courseClass.name")))
					.andExpect(
							jsonPath("$.errors[0].rejectedValue", nullValue()))
					.andExpect(jsonPath("$.errors[0].message",
							is("Class name is required.")));
		}

		@Test
		@DisplayName("When the class credit/unit is null")
		final void testCreateClass_withNullCredit() throws Exception {

			CourseClassDto courseClass = new CourseClassDto();
			courseClass.setName("classA");
			courseClass.setCredit(null);

			ResultActions resultActions = performPost("/classes", courseClass);

			assertBadRequest("Request has constraint violations", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors[0].field",
							is("add.courseClass.credit")))
					.andExpect(
							jsonPath("$.errors[0].rejectedValue", nullValue()))
					.andExpect(jsonPath("$.errors[0].message",
							is("Credit is required.")));
		}

		@Test
		@DisplayName("When the class credit/unit is invalid")
		final void testCreateClass_withInvalidCredit() throws Exception {

			CourseClassDto courseClass = new CourseClassDto();
			courseClass.setName("classA");
			courseClass.setCredit(10);

			ResultActions resultActions = performPost("/classes", courseClass);

			assertBadRequest("Request has constraint violations", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors[0].field",
							is("add.courseClass.credit")))
					.andExpect(jsonPath("$.errors[0].rejectedValue", is(10)))
					.andExpect(jsonPath("$.errors[0].message",
							is("Credit cannot exceed 4 per class.")));
		}

		@Test
		@DisplayName("When the input is valid")
		final void testCreateClass_withValidInput() throws Exception {

			ResultActions resultActions = performPost("/classes",
					mockedCourseClass());

			assertCreated(resultActions);
		}

	}

	@Nested
	@DisplayName("Test API to get class by name")
	class FindClassByName {
		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.CourseClassController#findClassByName(java.lang.String)}.
		 * 
		 * @throws Exception
		 */
		@Test
		@DisplayName("When no value in the path variable")
		final void testFindClassByName_noPathProvided() throws Exception {
			ResultActions resultActions = mockMvc.perform(get("/classes/"));

			assertMethodNotAllowed(resultActions);
		}

		@Test
		@DisplayName("When the input is valid but no record found")
		final void testFindClassByName_validInputNoRecordFound()
				throws Exception {
			String className = "classB";
			ResultActions resultActions = mockMvc
					.perform(get("/classes/{name}", className));

			assertNotFound("Class classB not found.", resultActions);
		}

		@Test
		@DisplayName("When the input is valid and the record is found")
		final void testFindClassByName_validRecordFound() throws Exception {

			courseClassRepository.save(mockedCourseClass());

			String className = "classA";
			ResultActions resultActions = mockMvc
					.perform(get("/classes/{name}", className));

			assertOK(resultActions).andExpect(jsonPath("$").isNotEmpty())
					.andExpect(jsonPath("$.name", is("classA")))
					.andExpect(jsonPath("$.credit", is(4)));

		}

	}

}
