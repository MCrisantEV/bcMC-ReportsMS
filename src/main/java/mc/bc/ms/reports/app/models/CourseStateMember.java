package mc.bc.ms.reports.app.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "institutes")
public class CourseStateMember {
	private String institute;
	private List<StudentState> listMember;
}
