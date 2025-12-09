package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import lombok.Data;

@Data
public class SchoolStatisticImpl implements SchoolStatistic {

    private int transcriptCount;
    private int dogwoodCount;
    private int adultDogwoodCount;
    private int frenchImmersionCount;
    private int programFrancophoneCount;
    private int evergreenCount;
    private int totalCertificateCount;

}
