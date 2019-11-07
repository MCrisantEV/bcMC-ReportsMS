package mc.bc.ms.reports.app.impl;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mc.bc.ms.reports.app.models.BestStudent;
import mc.bc.ms.reports.app.models.Course;
import mc.bc.ms.reports.app.models.Evaluation;
import mc.bc.ms.reports.app.models.EvaluationsCourse;
import mc.bc.ms.reports.app.models.EvaluationsInstitute;
import mc.bc.ms.reports.app.models.Family;
import mc.bc.ms.reports.app.models.Inscription;
import mc.bc.ms.reports.app.models.PersonEvaluation;
import mc.bc.ms.reports.app.repositories.InstituteRepository;
import mc.bc.ms.reports.app.services.FullEvaluationsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FullEvaluationImpl implements FullEvaluationsService {

	@Autowired
	private InstituteRepository intRep;

	@Autowired
	@Qualifier("course")
	private WebClient wcCourse;

	@Autowired
	@Qualifier("person")
	private WebClient wcPerson;

	@Autowired
	@Qualifier("inscription")
	private WebClient wcInscription;

	@Autowired
	@Qualifier("evaluation")
	private WebClient wcEvaluation;

	@Override
	public Mono<EvaluationsCourse> reportEvaluationsCourse(String institute, String course) {

		return intRep.findById(institute).flatMap(dbi -> {
			EvaluationsCourse ev = new EvaluationsCourse();
			ev.setInstitute(dbi.getInstitute());

			return wcCourse.get().uri("/" + course).accept(APPLICATION_JSON_UTF8).retrieve().bodyToMono(Course.class)
					.flatMap(dbc -> {
						ev.setCourse(dbc.getName());

						return wcPerson.get().uri("/" + dbc.getTeacher()).accept(APPLICATION_JSON_UTF8).retrieve()
								.bodyToMono(Family.class).map(dbp -> {
									ev.setTeacher(dbp.getNames() + " " + dbp.getLastNames());
									return ev;
								});
					});
		}).flatMap(ev -> {

			return wcInscription.get().uri("/" + course).accept(APPLICATION_JSON_UTF8).retrieve()
					.bodyToMono(Inscription.class).flatMap(dbins -> {

						Flux<PersonEvaluation> listStudent = Flux.fromIterable(dbins.getStudents()).flatMap(lst -> {
							PersonEvaluation pe = new PersonEvaluation();
							pe.setPerson(lst.getPerson());
							pe.setType("Student");

							return wcEvaluation.get().uri("/" + course + "/" + lst.getPerson())
									.accept(APPLICATION_JSON_UTF8).retrieve().bodyToMono(PersonEvaluation.class)
									.map(dbe -> {
										pe.setListEvaluation(dbe.getListEvaluation());

										double sum = 0;
										for (Double i : pe.getListEvaluation()) {
											sum = sum + i;
										}

										pe.setAverage(sum / (pe.getListEvaluation().size()));
										return pe;
									});
						});

						return Flux.fromIterable(dbins.getFamilyMembers()).map(ltins -> {
							PersonEvaluation pe = new PersonEvaluation();
							pe.setPerson(ltins.getPerson());
							pe.setType("FamilyMember");
							return pe;
						}).mergeWith(listStudent).collectList().map(lteva -> {
							double sum = 0;
							int student = 0;
							for (PersonEvaluation pe : lteva) {
								sum = sum + pe.getAverage();
								if (pe.getType().equals("Student")) {
									student = student + 1;
								}
							}
							ev.setTotalAverage(sum / student);
							ev.setList(lteva);

							return ev;
						});
					});
		}).flatMap(ev -> {
			return Flux.fromIterable(ev.getList()).flatMap(lts -> {
				return wcPerson.get().uri("/" + lts.getPerson()).accept(APPLICATION_JSON_UTF8).retrieve()
						.bodyToMono(Family.class).map(dbp -> {
							lts.setPerson(dbp.getNames() + " " + dbp.getLastNames());
							return lts;
						});
			}).collectList().map(lt -> {
				ev.setList(lt);
				return ev;
			});
		});

	}

	@Override
	public Flux<EvaluationsInstitute> reportEvaluationsInstitute() {

		return intRep.findAll().map(dbi -> {
			EvaluationsInstitute evi = new EvaluationsInstitute();
			evi.setId(dbi.getId());
			evi.setInstitute(dbi.getInstitute());
			return evi;
		}).flatMap(evi -> {
			return wcCourse.get().uri("/institute/" + evi.getId()).accept(APPLICATION_JSON_UTF8).retrieve()
					.bodyToFlux(Course.class).flatMap(dbc -> {
						BestStudent bs = new BestStudent();
						bs.setCourse(dbc.getName());
						
						return wcEvaluation.get().uri("/courses/" + dbc.getId())
						.accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Evaluation.class).map(dbe -> {
							double sum = 0;
							for (Double d : dbe.getListEvaluation()) {
								sum = sum + d;
							}
							dbe.setAverage(sum / dbe.getListEvaluation().size());
							return dbe;
						}).collectList().flatMap(lte -> {
//							bs.setList(lte);
							
							double mayor = 0;
							String person = null;
							
							for (Evaluation ev : lte) {
								if(ev.getAverage() > mayor) {
									mayor = ev.getAverage();
									person = ev.getIdStudent();
								}
							}
							bs.setAverage(mayor);
							
							return wcPerson.get().uri("/" + person).accept(APPLICATION_JSON_UTF8).retrieve()
							.bodyToMono(Family.class).map(dbp -> {
								bs.setPerson(dbp.getNames() + " " + dbp.getLastNames());
								return bs;
							});
						});
					}).collectList().map(lts -> {
						evi.setListBest(lts);
						
						double mayor = 0;
						double sum = 0;
						String person = null;
						
						for (BestStudent bs : lts) {
							sum = sum + bs.getAverage(); 
							if(bs.getAverage() > mayor) {
								mayor = bs.getAverage();
								person = bs.getPerson();
							}
						}
						
						evi.setTotalAverage(sum / lts.size());
						evi.setBestStudent(person);
						
						return evi;
					});
		});
	}

}
