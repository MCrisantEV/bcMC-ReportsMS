package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStatePerson;
import mc.bc.ms.reports.app.models.CourseStatePerson2;
import reactor.core.publisher.Mono;

public interface CourseStateStudentServ {
	
	public Mono<CourseStatePerson> reportCourseStateStudent(String id);
	
	public Mono<CourseStatePerson2> reportCourseStateStudentOp2(String id);
	
}
