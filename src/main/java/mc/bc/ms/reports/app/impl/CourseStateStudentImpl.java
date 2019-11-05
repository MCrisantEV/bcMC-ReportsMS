package mc.bc.ms.reports.app.impl;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mc.bc.ms.reports.app.models.Family;
import mc.bc.ms.reports.app.models.PersonState;
import mc.bc.ms.reports.app.models.PersonState2;
import mc.bc.ms.reports.app.models.Course;
import mc.bc.ms.reports.app.models.CourseStatePerson;
import mc.bc.ms.reports.app.models.CourseStatePerson2;
import mc.bc.ms.reports.app.repositories.InstituteRepository;
import mc.bc.ms.reports.app.services.CourseStateStudentServ;
import reactor.core.publisher.Mono;

@Service
public class CourseStateStudentImpl implements CourseStateStudentServ {

	@Autowired
	private InstituteRepository intRep;

	@Autowired
	@Qualifier("course")
	private WebClient wcCourse;

	@Autowired
	@Qualifier("family")
	private WebClient wcFamily;

	@Autowired
	@Qualifier("inscription")
	private WebClient wcInscription;

	@Override
	public Mono<CourseStatePerson> reportCourseStateStudent(String id) {
		String url = "/students/" + id;

		return intRep.findById(id).map(dbi -> {
			CourseStatePerson csp = new CourseStatePerson();
			csp.setInstitute(dbi.getInstitute());
			return csp;
		}).flatMap(csp -> {
			return wcFamily.get().uri(url).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Family.class)
					.flatMap(dbf -> {
						return wcInscription.get().uri("/students/" + dbf.getId() + "/" + id)
								.accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Course.class).flatMap(dbins -> {
									return wcCourse.get().uri("/" + dbins.getId()).accept(APPLICATION_JSON_UTF8)
											.retrieve().bodyToFlux(Course.class).map(dbc -> {
												PersonState ps = new PersonState();
												ps.setId(dbf.getId());
												ps.setPerson(dbf.getNames() + " " + dbf.getLastNames());
												if (dbc.getState().equals("Open")) {
													ps.setOpen(1);
												}
												if (dbc.getState().equals("Active")) {
													ps.setActive(1);
												}
												if (dbc.getState().equals("Complete")) {
													ps.setComplete(1);
												}
												return ps;
											});
								});
					}).collectList().map(lss -> {
						List<String> listDni = new ArrayList<>();

						for (PersonState fls : lss) {
							listDni.add(fls.getId());
						}

						listDni = listDni.stream().distinct().collect(Collectors.toList());

						List<PersonState> newList = new ArrayList<>();
						for (String sdni : listDni) {
							PersonState ps = new PersonState();
							ps.setId(sdni);
							int open = 0;
							int active = 0;
							int complete = 0;

							for (PersonState fls : lss) {
								if (fls.getId().equals(sdni)) {
									ps.setPerson(fls.getPerson());
									open = (fls.getOpen() == 1) ? open + 1 : open;
									active = (fls.getActive() == 1) ? active + 1 : active;
									complete = (fls.getComplete() == 1) ? complete + 1 : complete;
								}
							}

							ps.setOpen(open);
							ps.setActive(active);
							ps.setComplete(complete);
							newList.add(ps);
						}

						csp.setListPerson(newList);
						return csp;
					});
		});

	}

	@Override
	public Mono<CourseStatePerson2> reportCourseStateStudentOp2(String id) {
		String url = "/students/" + id;

		return intRep.findById(id).map(dbi -> {
			CourseStatePerson2 csp = new CourseStatePerson2();
			csp.setInstitute(dbi.getInstitute());
			return csp;
		}).flatMap(csp -> {
			return wcFamily.get().uri(url).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Family.class)
					.flatMap(dbf -> {
						return wcInscription.get().uri("/students/" + dbf.getId() + "/" + id)
								.accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Course.class).flatMap(dbins -> {
									return wcCourse.get().uri("/" + dbins.getId()).accept(APPLICATION_JSON_UTF8)
											.retrieve().bodyToFlux(Course.class).map(dbc -> {
												PersonState2 ps = new PersonState2();
												ps.setId(dbf.getId());
												ps.setPerson(dbf.getNames() + " " + dbf.getLastNames());
												ps.setState(dbc.getState());
												ps.setCourse(dbc.getName());
												return ps;
											});
								});
					}).collectList().map(lss -> {
						csp.setListPerson(lss);
						return csp;
					});
		});
	}

}
