package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client;

import lombok.Data;

import java.io.Serializable;

@Data

public class GradSearchStudent implements Serializable {

    private String studentID;
    private String pen;

}
