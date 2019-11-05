package mc.bc.ms.reports.app.models;

import lombok.Data;

@Data
public class PersonState {
	
	private String id;
	private String person;
	private int open;
	private int active;
	private int complete;
}
