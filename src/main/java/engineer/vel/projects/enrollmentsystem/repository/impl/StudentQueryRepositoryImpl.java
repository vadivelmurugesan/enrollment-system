package engineer.vel.projects.enrollmentsystem.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import com.mongodb.client.result.UpdateResult;

import engineer.vel.projects.enrollmentsystem.core.dto.SearchRequestDto;
import engineer.vel.projects.enrollmentsystem.repository.StudentQueryRepository;
import engineer.vel.projects.enrollmentsystem.repository.entity.CourseClass;
import engineer.vel.projects.enrollmentsystem.repository.entity.Student;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StudentQueryRepositoryImpl implements StudentQueryRepository {

	private static final String JSON_FIELD_SEMESTER = "semester";
	private static final String JSON_FIELD_CLASSES = "classes";
	private static final String JSON_FIELD_ENROLLMENTS = "enrollments";
	private static final String JSON_PATH_TO_CLASSES = JSON_FIELD_ENROLLMENTS
			+ ".$." + JSON_FIELD_CLASSES;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Student> fetchStudents(SearchRequestDto searchRequest) {

		log.debug("Building the query to fetch the students");

		final Query query = new Query().with(getPage(searchRequest));
		final List<Criteria> criteria = new ArrayList<>();

		// Adding id to the criteria
		if (searchRequest.getId() != null) {
			criteria.add(Criteria.where("id").is(searchRequest.getId()));
		}

		// Adding the semester in enrollments to the criteria
		if (StringUtils.isNotBlank(searchRequest.getSemester())) {
			criteria.add(Criteria.where(JSON_FIELD_ENROLLMENTS)
					.elemMatch(Criteria.where(JSON_FIELD_SEMESTER)
							.is(searchRequest.getSemester())));
		}

		// Adding the course class in enrollments to the criteria
		if (StringUtils.isNotBlank(searchRequest.getCourseClass())) {
			criteria.add(Criteria.where(JSON_FIELD_ENROLLMENTS)
					.elemMatch(Criteria.where(JSON_FIELD_CLASSES)
							.in(searchRequest.getCourseClass())));
		}

		// If the criteria is not empty then add them to the main query
		if (!criteria.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(
					criteria.toArray(new Criteria[criteria.size()])));
			log.debug("Built the query - {} ", query.toString());
		} else {
			log.debug(
					"Criteria are empty, so fetching all the students with pagination");
		}

		return mongoTemplate.find(query, Student.class);
	}

	/**
	 * 
	 * Gets the list of classes for a particular student for a semester, or the
	 * fully history of classes enrolled.
	 * 
	 * Equivalent Mongodb Query for below method
	 * <code> db.students.aggregate([{$match: {'_id': 7}},{$unwind : "$enrollments"}, {$unwind: '$enrollments.classes'}, {$match: {"enrollments.semester": "Fall2021"}},{ "$lookup": { "from": "classes", "localField" : "enrollments.classes", "foreignField" : "name", "as" : "classes"}},{$unwind:"$classes"},{ "$project": {"classes": 1 }}, {$replaceRoot: {newRoot: "$classes"}}]) 
	 * </code>
	 */
	@Override
	public List<CourseClass> fetchClasses(Long id, String semesterName) {

		log.debug("Building the aggregation query to fetch the classes");

		List<AggregationOperation> aggregations = new ArrayList<>();
		// Find matching student record by id ( '_' underscore is required for
		// Aggregation)
		aggregations.add(Aggregation.match(Criteria.where("_id").is(id)));
		aggregations.add(
				Aggregation.unwind("$enrollments", "$enrollments.classes"));

		// Filter by semester in the unwind listed classes
		if (StringUtils.isNotBlank(semesterName)) {
			aggregations.add(Aggregation.match(
					Criteria.where("enrollments.semester").is(semesterName)));
		}

		// Look up the matching class name in the 'classes' collection.
		aggregations.add(Aggregation.lookup(JSON_FIELD_CLASSES,
				"enrollments.classes", "name", JSON_FIELD_CLASSES));
		aggregations.add(Aggregation.unwind("$classes"));
		aggregations.add(Aggregation.project(JSON_FIELD_CLASSES));
		aggregations.add(Aggregation.replaceRoot("$classes"));

		Aggregation aggregation = Aggregation.newAggregation(aggregations);

		log.debug("Executing the aggregation query to fetch the classes {}",
				aggregation.toString());

		// Get the "classes" section from the students' collection and map them
		// to CourseClass type.
		AggregationResults<CourseClass> output = mongoTemplate
				.aggregate(aggregation, "students", CourseClass.class);

		log.debug("Retrieved the results from the database {} for the query",
				output.getServerUsed());

		return output.getMappedResults();
	}

	private Pageable getPage(SearchRequestDto searchRequest) {
		return PageRequest.of(searchRequest.getPageNo(),
				searchRequest.getPageSize(),
				Sort.by(Direction.fromString(searchRequest.getDirection()),
						searchRequest.getSortBy()));
	}

	@Override
	public boolean disenroll(Long id, String courseClass) {

		log.debug("Dropping student {} from class {}", id, courseClass);
		final Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id)
				.and(JSON_FIELD_ENROLLMENTS)
				.elemMatch(Criteria.where(JSON_FIELD_CLASSES).in(courseClass)));
		UpdateDefinition updateDef = new Update().pull(JSON_PATH_TO_CLASSES,
				courseClass);

		UpdateResult outputList = mongoTemplate.updateMulti(query, updateDef,
				Student.class);

		log.debug(
				"Executed Query to drop student {} from class {} : the result - matched count = {}, modified count = {} ",
				id, courseClass, outputList.getMatchedCount(),
				outputList.getModifiedCount());

		return outputList.wasAcknowledged();
	}

}