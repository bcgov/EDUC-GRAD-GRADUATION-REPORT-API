package ca.bc.gov.educ.api.grad.report.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class DistrictReportSpecifications {

  private DistrictReportSpecifications() {
    // Private constructor to prevent instantiation
  }

  public static <T> Specification<T> hasDistrictId(UUID districtId) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("districtId"), districtId);
  }

  public static <T> Specification<T> hasReportTypeCode(String reportTypeCode) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reportTypeCode"), reportTypeCode);
  }
}