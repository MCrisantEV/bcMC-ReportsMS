package mc.bc.ms.reports.app.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import mc.bc.ms.reports.app.models.CourseStatePerson;

public interface InstituteRepository extends ReactiveMongoRepository<CourseStatePerson, String>{
	
}
