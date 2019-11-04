package mc.bc.ms.reports.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mc.bc.ms.reports.app.models.CourseStateStudent;
import mc.bc.ms.reports.app.models.CourseStateStudent2;
import mc.bc.ms.reports.app.services.CourseStateStudentServ;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
public class ReportsControllers {
	
	@Autowired
	private CourseStateStudentServ cssServ;
	
	//Total de Estados de los cursos por estudiante de un instituto / Opcion 1
	@GetMapping("/coursestatestudent/op1/{id}")
	public Mono<CourseStateStudent> reportCourseStateStudent(@PathVariable String id){
		return cssServ.reportCourseStateStudent(id);
	}
	
	//Detalle de Estados por cursos por estudiante de un instituto / Opcion 2
	@GetMapping("/coursestatestudent/op2/{id}")
	public Mono<CourseStateStudent2> reportCourseStateStudentOp2(@PathVariable String id){
		return cssServ.reportCourseStateStudentOp2(id);
	}

}
