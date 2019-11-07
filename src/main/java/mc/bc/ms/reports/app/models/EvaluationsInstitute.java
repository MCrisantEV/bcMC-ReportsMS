package mc.bc.ms.reports.app.models;

import java.util.List;

import lombok.Data;

@Data
public class EvaluationsInstitute {
	private String id;
	private String institute;
	private double totalAverage;
	private String bestStudent;
	private List<BestStudent> listBest;
}	
