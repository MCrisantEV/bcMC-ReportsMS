package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStateStudent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseStateStudentServ {
	
	public Mono<CourseStateStudent> reportCourseStateStudent(String id);
	
}
