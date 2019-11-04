package mc.bc.ms.reports.app.impl;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mc.bc.ms.reports.app.models.CourseStateStudent;
import mc.bc.ms.reports.app.repositories.InstituteRepository;
import mc.bc.ms.reports.app.services.CourseStateStudentServ;
import reactor.core.publisher.Flux;
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
	public Mono<CourseStateStudent> reportCourseStateStudent(String id) {
		String url = "/students/" + id;

		return intRep.findById(id).map(dbi -> {
			CourseStateStudent css = new CourseStateStudent();
			css.setInstitute(dbi.getInstitute());
			return css;
		}).flatMap(css -> {
			return wcFamily.get().uri(url).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Family.class)
					.map(dbf -> {
						StudentState ss = new StudentState();
						ss.setId(dbf.getId());
						ss.setStudent(dbf.getNames() + " " + dbf.getLastNames());
						return ss;
					})
//					.map(mss -> {
//				
//				StudentState cntss = new StudentState();
//				
////				List<Integer> lstOpen = new ArrayList<>();
//				
//				wcInscription.get().uri("/students/"+dbf.getId()+"/"+id).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Course.class).flatMap(dbins -> {
//					return wcCourse.get().uri("/"+dbins.getId()).accept(APPLICATION_JSON_UTF8).retrieve().bodyToFlux(Course.class);
////						.map(dbc -> {
////						if(dbc.getState().equals("Open")) {
////							cntss.setOpen(cntss.getOpen() + 1);
////						}else if (dbc.getState().equals("Active")) {
////							cntss.setActive(cntss.getActive() + 1);
////						}else if (dbc.getState().equals("Complete")) {
////							cntss.setComplete(cntss.getComplete() + 1);
////						}
////						return cntss;
////					});
//				});
////				.flatMapMany(fm -> {
////					return Flux.fromIterable(fm).map(m -> {
////						if(m.getState().equals("Open")) {
////							lstOpen.add(1);
////						}
////						return lstOpen;
////					});
////				});
////				.map(lc -> {
////					if(lc.getState().equals("Open")) {
////						cntss.setOpen(1 +cntss.getOpen() + 1);
////					}
////					return cntss;
////				}).subscribe();
//
////				ss.setOpen(cntss.getOpen());
//				
//				return null;
//			})
					.collectList().map(lss -> {
						css.setListStudents(lss);
						return css;
					});
		});

	}

}
