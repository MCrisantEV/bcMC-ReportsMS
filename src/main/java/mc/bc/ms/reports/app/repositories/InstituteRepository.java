package mc.bc.ms.reports.app.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import mc.bc.ms.reports.app.models.CourseStateStudent;

public interface InstituteRepository extends ReactiveMongoRepository<CourseStateStudent, String>{
	
}
