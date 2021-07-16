package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GraduationStatus extends BaseModel {

	public GraduationStatus() {
        studentGradData = new StringBuffer();
    }

    private StringBuffer studentGradData;
    private String pen;
    private String gradProgram;
    private String graduationDate; 
    private String sccpGraduationDate;
    private String schoolAtGrad;
    private String gradProgramAtGraduation;
    private String studentGradeAtGraduation;
    private String gpa;
    private String honoursFlag;
    private String certificateType1;
    private String certificateType2;
    private String certificateType1Date; 
    private String certificateType2Date; 
    private String frenchProgramParticipation;
    private String advancePlacementParticipation;    
    private String careerProgramParticipation;
    private String recalculateFlag;
    private String dualDogwoodEligibility;
    private String ibParticipationFlag;    
    private String transcriptDate;  
    private String schoolOfRecord;
    private String studentGrade;
}
