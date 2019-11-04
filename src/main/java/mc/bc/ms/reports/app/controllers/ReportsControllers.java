package mc.bc.ms.reports.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mc.bc.ms.reports.app.services.CourseStateStudentServ;

@RestController
@RequestMapping("/reports")
public class ReportsControllers {
	
	@Autowired
	private CourseStateStudentServ cssServ;

}
