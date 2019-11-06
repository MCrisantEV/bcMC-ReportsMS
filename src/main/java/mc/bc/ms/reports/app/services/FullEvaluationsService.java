package mc.bc.ms.reports.app.services;

import mc.bc.ms.reports.app.models.EvaluationsCourse;
import reactor.core.publisher.Mono;

public interface FullEvaluationsService {
	
	public Mono<EvaluationsCourse> reportEvaluationsCourse(String institute, String course);
}
