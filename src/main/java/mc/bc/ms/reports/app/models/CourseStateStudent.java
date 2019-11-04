package mc.bc.ms.reports.app.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import mc.bc.ms.reports.app.impl.StudentState;

@Data
@Document(collection = "institutes")
public class CourseStateStudent {
	private String institute;
	private List<StudentState> listStudents;
}
