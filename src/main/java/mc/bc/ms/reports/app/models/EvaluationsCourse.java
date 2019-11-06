package mc.bc.ms.reports.app.models;

import java.util.List;

import lombok.Data;

@Data
public class EvaluationsCourse {
	private String institute;
	private String course;
	private String teacher;
	private double totalAverage;
	private List<PersonEvaluation> list;
}
