package ca.bc.gov.educ.api.grad.report.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportGradStudentData implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID graduationStudentRecordId;
    private String mincode;
    private String mincodeAtGrad;
    private String pen;
    private String firstName;
    private String middleName;
    private String lastName;
    private String studentGrade;
    private String studentStatus;
    private String districtName;
    private String schoolName;
    private String schoolAddress1;
    private String schoolAddress2;
    private String schoolCity;
    private String schoolProvince;
    private String schoolCountry;
    private String schoolPostal;
    private String programCode;
    private String programName;
    private String programCompletionDate;
    private String graduated;
    private String transcriptTypeCode;
    private String certificateTypeCode;
    private String paperType;
    private LocalDateTime updateDate;
    private List<GradCertificateTypes> certificateTypes;

}
