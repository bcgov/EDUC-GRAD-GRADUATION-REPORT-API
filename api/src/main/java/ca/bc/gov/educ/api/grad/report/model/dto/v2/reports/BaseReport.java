package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import lombok.Data;

import java.util.UUID;

@Data
public class BaseReport {
  private UUID id;
  private String report;
  private String reportTypeCode;
  private String reportTypeLabel;
}
