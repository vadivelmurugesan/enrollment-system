package engineer.vel.projects.enrollmentsystem.service.impl;

import static engineer.vel.projects.enrollmentsystem.core.constant.AppConstants.MAX_CREDIT_PER_SEMESTER;
import static engineer.vel.projects.enrollmentsystem.core.constant.AppConstants.MIN_CREDIT_FOR_FULLTIME;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import engineer.vel.projects.enrollmentsystem.core.dto.CourseClassDto;
import engineer.vel.projects.enrollmentsystem.core.dto.EnrollmentRequestDto;
import engineer.vel.projects.enrollmentsystem.core.dto.SemesterDto;
import engineer.vel.projects.enrollmentsystem.core.exception.InvalidEnrollmentRequestException;
import engineer.vel.projects.enrollmentsystem.core.exception.StudentInfoNotFoundException;
import engineer.vel.projects.enrollmentsystem.repository.StudentRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.Enrollment;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import engineer.vel.projects.enrollmentsystem.service.CourseClassService;
import engineer.vel.projects.enrollmentsystem.service.EnrollmentService;
import engineer.vel.projects.enrollmentsystem.service.SemesterService;
import lombok.extern.log4j.Log4j2;

/**
 * An implementation class for {@link EnrollmentService}.
 * 
 * @author Vadivel Murugesan
 *
 */
@Log4j2
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	private CourseClassService courseClassService;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public void enroll(EnrollmentRequestDto enrollReq) {
		log.debug(
				"Enrolling the student {} into the class {} for the semester {}",
				enrollReq.getStudentId(), enrollReq.getSemester(),
				enrollReq.getCourseClass());

		Student fetchedStudent = findOrElesThrowEx(enrollReq.getStudentId());

		SemesterDto semester = semesterService
				.findByName(enrollReq.getSemester());

		log.debug(
				"Finding the existing enrollments for the student {} and the semester {}",
				fetchedStudent.getId(), semester.getName());

		Optional<Enrollment> existingEnrollmentOpt = findExistingEnrollment(
				fetchedStudent, semester);

		Enrollment enrollment = null;
		if (existingEnrollmentOpt.isPresent()) {
			log.debug(
					"Found the current enrollments for the student {} and the semester {}",
					fetchedStudent.getId(), semester.getName());
			enrollment = existingEnrollmentOpt.get();
		} else {
			log.debug(
					"No current enrollments for the student {} and the semester {}",
					fetchedStudent.getId(), semester.getName());
			enrollment = new Enrollment();
			enrollment.setSemester(semester.getName());
			fetchedStudent.addEnrollment(enrollment);
		}

		enrollment.addCourseClass(enrollReq.getCourseClass());

		int sumOfCredits = sumOfCredits(enrollment);

		log.debug("Total credit for the semester - {} = {} ",
				semester.getName(), sumOfCredits);

		Boolean isFullTime = Boolean.FALSE;

		// Each student is only allowed to be enrolled in a maximum of 20
		// credits for
		// each semester.
		if (sumOfCredits > MAX_CREDIT_PER_SEMESTER) {
			log.error("Total credit {} for the semester exceeds the limit {}",
					sumOfCredits, MAX_CREDIT_PER_SEMESTER);

			throw new InvalidEnrollmentRequestException();
		}
		// There is a minimum of 10 credits to be considered full time.
		else if (sumOfCredits >= MIN_CREDIT_FOR_FULLTIME) {

			log.error(
					"Total credit {} for the semester exceeds the minimum limit {} to be considered full time",
					sumOfCredits, MAX_CREDIT_PER_SEMESTER);

			isFullTime = Boolean.TRUE;
		}

		enrollment.setIsFullTime(isFullTime);

		studentRepository.save(fetchedStudent);
	}

	@Override
	public void disenroll(Long id, String courseClass) {
		log.debug("Dropping the student {} from the class {}", id, courseClass);
		studentRepository.disenroll(id, courseClass);
	}

	private Optional<Enrollment> findExistingEnrollment(Student fetchedStudent,
			SemesterDto semester) {
		return fetchedStudent.getEnrollments() == null
				? Optional.empty()
				: fetchedStudent.getEnrollments().stream()
						.filter(existingEnroll -> StringUtils.equalsIgnoreCase(
								existingEnroll.getSemester(),
								semester.getName()))
						.findFirst();
	}

	private int sumOfCredits(Enrollment enrollment) {
		return findDocumentsByNames(enrollment.getClasses()).stream()
				.mapToInt(CourseClassDto::getCredit).sum();
	}

	private List<CourseClassDto> findDocumentsByNames(Set<String> classes) {
		return classes.stream()
				.map(courseClass -> courseClassService.findByName(courseClass))
				.collect(Collectors.toList());
	}

	private Student findOrElesThrowEx(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new StudentInfoNotFoundException(id));
	}

}
