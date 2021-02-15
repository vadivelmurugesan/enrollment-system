package engineer.vel.projects.enrollmentsystem.core.validation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * An Annotation to mark the field student identifier field to be unique. It
 * uses {@link UniqueStudentIdValidator} type to validate the given id with the
 * database.
 * 
 * @author Vadivel Murugesan
 *
 */
@Constraint(validatedBy = {UniqueStudentIdValidator.class})
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueStudentId {
	String message() default "Student id must be unique.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}