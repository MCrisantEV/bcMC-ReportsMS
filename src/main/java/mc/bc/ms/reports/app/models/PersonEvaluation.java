package mc.bc.ms.reports.app.models;

import java.util.List;

import lombok.Data;

@Data
public class PersonEvaluation {	
	private String person;
	private String type;
	private List<Double> listEvaluation;
	private double average;
}
