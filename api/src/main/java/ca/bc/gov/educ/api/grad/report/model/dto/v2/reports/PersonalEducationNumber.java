package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import lombok.Getter;

@Getter
public class PersonalEducationNumber {

    public static final PersonalEducationNumber NULL = new PersonalEducationNumber("", "");
    
    private String pen;
    private String entityId;

    public PersonalEducationNumber() {}

    public PersonalEducationNumber(String pen) {
        this.pen = pen;
    }

    public PersonalEducationNumber(String pen, String entityId) {
        this.pen = pen;
        this.entityId = entityId;
    }
    
    public String getValue() {
        return pen;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setPen(String pen) {
        this.pen = pen;
    }
    
    public String toString() {
        return getPen();
    }
}
