package mc.bc.ms.reports.app.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import mc.bc.ms.reports.app.models.Institute;

public interface InstituteRepository extends ReactiveMongoRepository<Institute, String>{
	
}
