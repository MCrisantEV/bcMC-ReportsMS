package mc.bc.ms.reports.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mc.bc.ms.reports.app.repositories.InstituteRepository;
import mc.bc.ms.reports.app.services.CourseStateStudentServ;

@Service
public class CourseStateStudentImpl implements CourseStateStudentServ {
	
	@Autowired
	private InstituteRepository intRep;

}
