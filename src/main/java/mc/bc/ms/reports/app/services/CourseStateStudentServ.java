package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStateStudent;
import mc.bc.ms.reports.app.models.CourseStateStudent2;
import reactor.core.publisher.Mono;

public interface CourseStateStudentServ {
	
	public Mono<CourseStateStudent> reportCourseStateStudent(String id);
	
	public Mono<CourseStateStudent2> reportCourseStateStudentOp2(String id);
	
}
