package mc.bc.ms.reports.app.models;

import lombok.Data;

@Data
public class StudentState {
	
	private String id;
	private String student;
	private int open;
	private int active;
	private int complete;
}
