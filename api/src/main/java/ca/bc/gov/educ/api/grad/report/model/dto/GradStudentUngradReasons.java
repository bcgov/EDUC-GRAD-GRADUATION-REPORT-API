package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class GradStudentUngradReasons extends BaseModel {

	private UUID id;
	private UUID studentID;
	private String pen;
	private String ungradReasonCode;
	private String ungradReasonName;
	private String ungradReasonDescription;

	@Override
	public String toString() {
		return "GradStudentUngradReasons [id=" + id + ", pen=" + pen + ", ungradReasonCode=" + ungradReasonCode
				+ ", ungradReasonName=" + ungradReasonName + ", createdBy=" + this.getCreatedBy() + ", createdTimestamp="
				+ getCreatedTimestamp() + ", updatedBy=" + getUpdatedBy() + ", updatedTimestamp=" + getUpdatedTimestamp() + "]";
	}
	
	
	
	
	
}
