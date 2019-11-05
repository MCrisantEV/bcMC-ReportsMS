package mc.bc.ms.reports.app.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "institutes")
public class CourseStateMember2 {
	private String institute;
	private List<StudentState2> listMember;
}
