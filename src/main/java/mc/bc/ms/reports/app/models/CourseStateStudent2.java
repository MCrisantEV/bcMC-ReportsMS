package mc.bc.ms.reports.app.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "institutes")
public class CourseStateStudent2 {
	private String institute;
	private List<StudentState2> listStudents;
}
