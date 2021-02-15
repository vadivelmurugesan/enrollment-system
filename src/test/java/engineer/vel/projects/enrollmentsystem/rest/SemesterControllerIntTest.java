/**
 * 
 */
package engineer.vel.projects.enrollmentsystem.rest;

import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertBadRequest;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertCreated;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertMethodNotAllowed;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertNotFound;
import static engineer.vel.projects.enrollmentsystem.test.AssertionHelper.assertOK;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemester;
import static engineer.vel.projects.enrollmentsystem.test.MockedObjects.mockedSemesterDto;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.repository.SemesterRepository;
import engineer.vel.projects.enrollmentsystem.test.IntegrationTestBase;

/**
 * Tests all {@link SemesterController} APIs.
 * 
 * @author Vadivel Murugesan
 *
 */
class SemesterControllerIntTest extends IntegrationTestBase {

	private static final String BASE_CONTEXT_PATH = "/semesters";

	@Autowired
	private SemesterRepository semesterRepository;

	@Nested
	@DisplayName("API to add a new semester")
	class CreateSemester {

		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.SemesterController#createSemester(engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto)}.
		 */
		@Test
		@DisplayName("When no content in the body")
		final void testCreateSemester_withoutBody() throws Exception {
			ResultActions resultActions = mockMvc
					.perform(post(BASE_CONTEXT_PATH));

			assertBadRequest("Malformed JSON request", resultActions);
		}

		@Test
		@DisplayName("When the semester name is null")
		final void testCreateSemester_withNullName() throws Exception {

			SemesterDto semester = new SemesterDto();
			semester.setName(null);

			ResultActions resultActions = performPost(BASE_CONTEXT_PATH,
					semester);

			assertBadRequest("Request has constraint violations", resultActions)
					.andExpect(jsonPath("$.errors").isArray())
					.andExpect(jsonPath("$.errors", hasSize(1)))
					.andExpect(jsonPath("$.errors[0].field",
							is("add.semester.name")))
					.andExpect(
							jsonPath("$.errors[0].rejectedValue", nullValue()))
					.andExpect(jsonPath("$.errors[0].message",
							is("Semester name is required.")));
		}

		@Test
		@DisplayName("When the input is valid")
		final void testCreateSemester_withValidInput() throws Exception {

			ResultActions resultActions = performPost(BASE_CONTEXT_PATH,
					mockedSemesterDto());

			assertCreated(resultActions);
		}

	}

	@Nested
	@DisplayName("API to get the semester by name")
	class FindSemesterByName {
		/**
		 * Test method for
		 * {@link engineer.vel.projects.enrollmentsystem.rest.SemesterController#findSemesterByName(java.lang.String)}.
		 */
		@Test
		@DisplayName("When no value in the path variable")
		final void testFindSemesterByName_noPathProvided() throws Exception {
			ResultActions resultActions = mockMvc
					.perform(get(BASE_CONTEXT_PATH));

			assertMethodNotAllowed(resultActions);
		}

		@Test
		@DisplayName("When the input is valid but no record found")
		final void testFindSemesterByName_validInputNoRecordFound()
				throws Exception {
			ResultActions resultActions = mockMvc
					.perform(get(BASE_CONTEXT_PATH + "/{name}", "Spring2022"));

			assertNotFound("Semester Spring2022 not found.", resultActions);
		}

		@Test
		@DisplayName("When the input is valid and the record is found")
		final void testFindSemesterByName_validRecordFound() throws Exception {

			semesterRepository.save(mockedSemester());

			ResultActions resultActions = mockMvc
					.perform(get(BASE_CONTEXT_PATH + "/{name}", "Spring2021"));

			assertOK(resultActions).andExpect(jsonPath("$").isNotEmpty())
					.andExpect(jsonPath("$.name", is("Spring2021")))
					.andExpect(jsonPath("$.startDate", notNullValue()));

		}

	}

}
