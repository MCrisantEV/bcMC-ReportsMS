package mc.bc.ms.reports.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mc.bc.ms.reports.app.models.CourseStateStudent;
import mc.bc.ms.reports.app.services.CourseStateStudentServ;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
public class ReportsControllers {
	
	@Autowired
	private CourseStateStudentServ cssServ;
	
	@GetMapping("/coursestatestudent/{id}")
	public Mono<CourseStateStudent> reportCourseStateStudent(@PathVariable String id){
		return cssServ.reportCourseStateStudent(id);
	}

}
