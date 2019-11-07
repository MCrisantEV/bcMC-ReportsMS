package mc.bc.ms.reports.app.models;

import java.util.List;

import lombok.Data;

@Data
public class Evaluation {
	private String idStudent;
	private double average;
	private List<Double> listEvaluation;
}
