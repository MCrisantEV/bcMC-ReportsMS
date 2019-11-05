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
import mc.bc.ms.reports.app.models.StudentState;
import mc.bc.ms.reports.app.models.StudentState2;
import mc.bc.ms.reports.app.models.Course;
import mc.bc.ms.reports.app.models.CourseStateMember;
import mc.bc.ms.reports.app.models.CourseStateMember2;
import mc.bc.ms.reports.app.repositories.InstituteRepository;
import mc.bc.ms.reports.app.services.CourseStateMemberServ;
import reactor.core.publisher.Mono;

@Service
public class CourseStateMemberImpl implements CourseStateMemberServ {

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
	public Mono<CourseStateMember> reportCourseStateMember(String id) {
		String url = "/members/" + id;

		return intRep.findById(id).map(dbi -> {
			CourseStateMember csm = new CourseStateMember();
			csm.setInstitute(dbi.getInstitute());
			return csm;
		}).flatMap(csm -> {
			return wcFamily.get().uri(url).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Family.class)
					.flatMap(dbf -> {
						return wcInscription.get().uri("/members/" + dbf.getId() + "/" + id)
								.accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Course.class).flatMap(dbins -> {
									return wcCourse.get().uri("/" + dbins.getId()).accept(APPLICATION_JSON_UTF8)
											.retrieve().bodyToFlux(Course.class).map(dbc -> {
												StudentState ss = new StudentState();
												ss.setId(dbf.getId());
												ss.setStudent(dbf.getNames() + " " + dbf.getLastNames());
												if (dbc.getState().equals("Open")) {
													ss.setOpen(1);
												}
												if (dbc.getState().equals("Active")) {
													ss.setActive(1);
												}
												if (dbc.getState().equals("Complete")) {
													ss.setComplete(1);
												}
												return ss;

											});
								});
					}).collectList().map(lss -> {
						List<String> listDni = new ArrayList<>();

						for (StudentState fls : lss) {
							listDni.add(fls.getId());
						}

						listDni = listDni.stream().distinct().collect(Collectors.toList());

						List<StudentState> newList = new ArrayList<>();
						for (String sdni : listDni) {
							StudentState ss = new StudentState();
							ss.setId(sdni);
							int open = 0;
							int active = 0;
							int complete = 0;

							for (StudentState fls : lss) {
								if (fls.getId().equals(sdni)) {
									ss.setStudent(fls.getStudent());
									open = (fls.getOpen() == 1) ? open + 1 : open;
									active = (fls.getActive() == 1) ? active + 1 : active;
									complete = (fls.getComplete() == 1) ? complete + 1 : complete;
								}
							}

							ss.setOpen(open);
							ss.setActive(active);
							ss.setComplete(complete);
							newList.add(ss);
						}

						csm.setListMember(newList);
						return csm;
					});
		});

	}

	@Override
	public Mono<CourseStateMember2> reportCourseStateMemberOp2(String id) {
		String url = "/members/" + id;

		return intRep.findById(id).map(dbi -> {
			CourseStateMember2 csm = new CourseStateMember2();
			csm.setInstitute(dbi.getInstitute());
			return csm;
		}).flatMap(csm -> {
			return wcFamily.get().uri(url).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Family.class)
					.flatMap(dbf -> {
						return wcInscription.get().uri("/members/" + dbf.getId() + "/" + id)
								.accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Course.class).flatMap(dbins -> {
									return wcCourse.get().uri("/" + dbins.getId()).accept(APPLICATION_JSON_UTF8)
											.retrieve().bodyToFlux(Course.class).map(dbc -> {
												StudentState2 ss = new StudentState2();
												ss.setId(dbf.getId());
												ss.setStudent(dbf.getNames() + " " + dbf.getLastNames());
												ss.setState(dbc.getState());
												ss.setCourse(dbc.getName());
												return ss;

											});
								});
					}).collectList().map(lss -> {
						csm.setListMember(lss);
						return csm;
					});
		});
	}

}

