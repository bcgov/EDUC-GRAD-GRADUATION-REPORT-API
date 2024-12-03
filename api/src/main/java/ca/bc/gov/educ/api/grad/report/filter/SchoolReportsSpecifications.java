package ca.bc.gov.educ.api.grad.report.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SchoolReportsSpecifications {

  private SchoolReportsSpecifications() {
    // Private constructor to prevent instantiation
  }

  public static <T> Specification<T> hasSchoolOfRecordId(UUID schoolOfRecordId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("schoolOfRecordId"), schoolOfRecordId);
  }

  public static <T> Specification<T> hasReportTypeCode(String reportTypeCode) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reportTypeCode"), reportTypeCode);
  }
}