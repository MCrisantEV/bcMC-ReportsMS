package mc.bc.ms.reports.app.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "institutes")
public class Institute {
	private String id;
	private String institute;
}
