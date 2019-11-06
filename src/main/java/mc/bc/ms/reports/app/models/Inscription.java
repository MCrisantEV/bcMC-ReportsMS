package mc.bc.ms.reports.app.models;

import java.util.List;

import lombok.Data;

@Data
public class Inscription {
	private List<PersonState2> students;
	private List<PersonState2> familyMembers;
}
