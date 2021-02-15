package engineer.vel.projects.enrollmentsystem.test;

import java.time.LocalDate;

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto;
import engineer.vel.projects.enrollmentsystem.core.dto.SearchRequestDto;
import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.core.dto.StudentRequestDto;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
import engineer.vel.projects.enrollmentsystem.repository.entity.Enrollment;
import engineer.vel.projects.enrollmentsystem.repository.entity.Semester;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;

/**
 * Mocked Objects for Api's junits.
 * 
 * @author Vadivel Murugesan
 *
 */
public final class MockedObjects {

	private MockedObjects() {

	}

	public static Student mockedStudent() {
		Student student = new Student();
		student.setId(1L);
		student.setFirstName("TestFN");
		student.setLastName("TestLN");
		student.setNationality("US");
		Enrollment enrollment = new Enrollment();
		enrollment.addCourseClass(mockedCourseClass().getName());
		enrollment.setSemester(mockedSemester().getName());
		student.addEnrollment(enrollment);
		return student;
	}

	public static Semester mockedSemester() {
		Semester semester = new Semester();
		semester.setName("Spring2021");
		semester.setStartDate(LocalDate.now());
		semester.setEndDate(LocalDate.now().plusDays(90));
		return semester;
	}

	public static SemesterDto mockedSemesterDto() {
		SemesterDto semester = new SemesterDto();
		semester.setName("Spring2021");
		semester.setStartDate(LocalDate.now());
		semester.setEndDate(LocalDate.now().plusDays(90));
		return semester;
	}

	public static CourseClass mockedCourseClass() {
		return mockCourseClass("classA");
	}

	public static CourseClass mockCourseClass(String courseClassString) {
		CourseClass courseClass = new CourseClass();
		courseClass.setName(courseClassString);
		courseClass.setCredit(4);
		return courseClass;
	}

	public static CourseClassDto mockedCourseClassDto() {
		CourseClassDto courseClass = new CourseClassDto();
		courseClass.setName("classA");
		courseClass.setCredit(4);
		return courseClass;
	}

	public static StudentRequestDto mockedStudentRequest() {
		StudentRequestDto student = new StudentRequestDto();
		student.setId(1L);
		student.setFirstName("TestFN");
		student.setLastName("TestLN");
		student.setNationality("US");
		return student;
	}

	public static EnrollmentRequestDto mockedEnrollmentRequestDto() {
		EnrollmentRequestDto request = new EnrollmentRequestDto();
		request.setCourseClass("classB");
		request.setSemester("Spring2021");
		request.setStudentId(1L);
		return request;
	}

	public static SearchRequestDto mockedSearchRequestDto() {
		SearchRequestDto request = new SearchRequestDto();
		request.setId(1L);
		request.setCourseClass("classA");
		request.setSemester("Spring2021");
		request.setPageNo(1);
		request.setPageSize(10);
		request.setSortBy(new String[]{"id"});
		request.setDirection("ASC");
		return request;
	}

}
