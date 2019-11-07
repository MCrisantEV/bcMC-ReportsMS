package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.EvaluationsCourse;
import mc.bc.ms.reports.app.models.EvaluationsInstitute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FullEvaluationsService {
	
	public Mono<EvaluationsCourse> reportEvaluationsCourse(String institute, String course);
	
	public Flux<EvaluationsInstitute> reportEvaluationsInstitute();
}
