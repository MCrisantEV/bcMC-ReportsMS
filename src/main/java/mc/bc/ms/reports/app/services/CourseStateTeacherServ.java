package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStatePerson;
import mc.bc.ms.reports.app.models.CourseStatePerson2;
import reactor.core.publisher.Mono;

public interface CourseStateTeacherServ {
	
	public Mono<CourseStatePerson> reportCourseStateTeacher(String id);
	
	public Mono<CourseStatePerson2> reportCourseStateTeacherOp2(String id);
	
}
