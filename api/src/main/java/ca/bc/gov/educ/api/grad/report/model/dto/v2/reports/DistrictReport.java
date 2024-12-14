package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class DistrictReport extends BaseReport {
  private UUID districtId;
  private String districtName;
}
