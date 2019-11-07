package mc.bc.ms.reports.app.models;

import java.util.List;

import lombok.Data;

@Data
public class CourseStatePerson {
	private String institute;
	private List<PersonState> listPerson;
}
