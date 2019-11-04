package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.CourseStateMember;

import reactor.core.publisher.Mono;

public interface CourseStateMemberServ {
	
	public Mono<CourseStateMember> reportCourseStateStudent(String id);
	
//	public Mono<CourseStateStudent2> reportCourseStateStudentOp2(String id);
	
}
