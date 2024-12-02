package ca.bc.gov.educ.api.grad.report.filter;

import ca.bc.gov.educ.api.grad.report.model.entity.SchoolReportsEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SchoolReportsSpecifications {

  private SchoolReportsSpecifications() {
    // Private constructor to prevent instantiation
  }

  public static Specification<SchoolReportsEntity> hasSchoolOfRecordId(UUID schoolOfRecordId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("schoolOfRecordId"), schoolOfRecordId);
  }

  public static Specification<SchoolReportsEntity> hasReportTypeCode(String reportTypeCode) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reportTypeCode"), reportTypeCode);
  }
}