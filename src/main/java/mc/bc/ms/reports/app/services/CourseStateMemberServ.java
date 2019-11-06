package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStatePerson;
import mc.bc.ms.reports.app.models.CourseStatePerson2;
import reactor.core.publisher.Mono;

public interface CourseStateMemberServ {
	
	public Mono<CourseStatePerson> reportCourseStateMember(String id);
	
	public Mono<CourseStatePerson2> reportCourseStateMemberOp2(String id);
	
	public Mono<CourseStatePerson2> reportCourseMemberType(String id);
	
}
