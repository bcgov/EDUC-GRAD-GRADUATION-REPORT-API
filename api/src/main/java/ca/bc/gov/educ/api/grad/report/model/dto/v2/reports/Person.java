package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface Person extends Party {

    String getLastName();

    String getFirstName();

    String getMiddleName();

    String getFullName();
}
