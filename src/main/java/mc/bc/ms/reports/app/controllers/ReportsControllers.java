package mc.bc.ms.reports.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mc.bc.ms.reports.app.models.CourseStatePerson;
import mc.bc.ms.reports.app.models.CourseStatePerson2;
import mc.bc.ms.reports.app.services.CourseStateMemberServ;
import mc.bc.ms.reports.app.services.CourseStateStudentServ;
import mc.bc.ms.reports.app.services.CourseStateTeacherServ;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
public class ReportsControllers {
	
	@Autowired
	private CourseStateStudentServ cssServ;
	
	@Autowired
	private CourseStateMemberServ csmServ;
	
	@Autowired
	private CourseStateTeacherServ cstServ;
	
//	Total de Estados de los cursos por estudiante de un instituto / Opcion 1
	@GetMapping("/csstudent/op1/{id}")
	public Mono<CourseStatePerson> reportCourseStateStudent(@PathVariable String id){
		return cssServ.reportCourseStateStudent(id);
	}
//	Detalle de Estados por cursos por estudiante de un instituto / Opcion 2
	@GetMapping("/csstudent/op2/{id}")
	public Mono<CourseStatePerson2> reportCourseStateStudentOp2(@PathVariable String id){
		return cssServ.reportCourseStateStudentOp2(id);
	}
	
	
//	Total de Estados de los cursos por Familiares de un instituto / Opcion 1
	@GetMapping("/csmember/op1/{id}")
	public Mono<CourseStatePerson> reportCourseStateMember(@PathVariable String id){
		return csmServ.reportCourseStateMember(id);
	}
//	Detalle de Estados por cursos por Familiares de un instituto / Opcion 2
	@GetMapping("/csmember/op2/{id}")
	public Mono<CourseStatePerson2> reportCourseStateMemberOp2(@PathVariable String id){
		return csmServ.reportCourseStateMemberOp2(id);
	}
	
	
//	Total de Estados de los cursos por Profesores de un instituto / Opcion 1
	@GetMapping("/csteachers/op1/{id}")
	public Mono<CourseStatePerson> reportCourseStatePerson(@PathVariable String id){
		return cstServ.reportCourseStateTeacher(id);
	}
//	Detalle de Estados por cursos por profesores de un instituto / Opcion 2
	@GetMapping("/csteachers/op2/{id}")
	public Mono<CourseStatePerson2> reportCourseStatePersonOp2(@PathVariable String id){
		return cstServ.reportCourseStateTeacherOp2(id);
	}
	
//	Cursos tomados por familiares por estudiante o profesor 
	@GetMapping("/cftype/{id}")
	public Mono<CourseStatePerson2> reportCourseMemberType(@PathVariable String id){
		return csmServ.reportCourseMemberType(id);
	}

}
