package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStateMember;
import mc.bc.ms.reports.app.models.CourseStateMember2;
import reactor.core.publisher.Mono;

public interface CourseStateMemberServ {
	
	public Mono<CourseStateMember> reportCourseStateMember(String id);
	
	public Mono<CourseStateMember2> reportCourseStateMemberOp2(String id);
	
}
