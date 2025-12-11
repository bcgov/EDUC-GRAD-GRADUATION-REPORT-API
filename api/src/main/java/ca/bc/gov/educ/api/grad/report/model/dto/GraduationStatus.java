package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GraduationStatus extends BaseModel{

    private String studentGradData;
    private String pen;
    private String program;
    private String programCompletionDate;
    private String gpa;
    private String honoursStanding;
    private String recalculateGradStatus;   
    private String schoolOfRecord;
    private String studentGrade;
    private UUID studentID;
    private String schoolAtGrad;
    private String studentStatus;
				
}
