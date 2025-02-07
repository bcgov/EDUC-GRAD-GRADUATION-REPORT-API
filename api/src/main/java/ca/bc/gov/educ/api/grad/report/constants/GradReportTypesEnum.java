package ca.bc.gov.educ.api.grad.report.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum GradReportTypesEnum {
  TVRGRAD("TVRGRAD", "TVRs for Projected Graduating Students"),
  TVRNONGRAD("TVRNONGRAD", "TVRs for Projected Non-Graduating Students"),
  GRADREGARC("GRADREGARC", "Archived Graduated Students (MM YYYY to MM YYYY) Report"),
  NONGRADREGARC("NONGRADREGARC", "Archived Not-Yet Graduated Students (MM YYYY to MM YYYY) Report"),
  NONGRADPRJARC("NONGRADPRJARC", "Archived Projected Non-Graduates - Summary Report (MM YYYY to MM YYYY)"),
  NONGRADPRJ("NONGRADPRJ", "Projected Non-Graduates - Summary Report (MM YYYY to MM YYYY)"),
  ACHV("ACHV", "Transcript Verification Report"),
  GRADREG("GRADREG", "Graduated Students (MM YYYY to MM YYYY) Report"),
  DISTREP_SC("DISTREP_SC", "Credentials and Transcript Distribution Report"),
  DISTREP_YE_SC("DISTREP_YE_SC", "Year-End Credentials and Transcript Distribution Report (School)"),
  DISTREP_YE_SD("DISTREP_YE_SD", "Year-End District Credentials and Transcript Distribution Report (District)"),
  NONGRADDISTREP_SC("NONGRADDISTREP_SC", "Non-Graduation Transcript Distribution Report (School)"),
  NONGRADDISTREP_SD("NONGRADDISTREP_SD", "Non-Graduation Transcript Distribution Report (District)"),
  GRADPRJ("GRADPRJ", "Projected Graduates - Summary Report (MM YYYY to MM YYYY)"),
  GRADPRJARC("GRADPRJARC", "Archived Projected Graduates - Summary Report (MM YYYY to MM YYYY)"),
  ADDRESS_LABEL_SCHL("ADDRESS_LABEL_SCHL", "Mailing Labels - School"),
  ADDRESS_LABEL_YE("ADDRESS_LABEL_YE", "Year-End Mailing Labels - School and District"),
  ADDRESS_LABEL_PSI("ADDRESS_LABEL_PSI", "Year-End Mailing Labels - PSIs"),
  NONGRADREG("NONGRADREG", "Not-Yet Graduated Students (MM YYYY to MM YYYY) Report"),
  XML("XML", "XML Preview"),
  TRAN("TRAN", "Student Transcript"),
  ADDRESS_LABEL("ADDRESS_LABEL", "Mailing Labels - School and District"),
  DISTREP_SD("DISTREP_SD", "District Credentials and Transcript Distribution Report (District)");

  private final String code;
  private final String label;

  GradReportTypesEnum(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public static Optional<GradReportTypesEnum> findByValue(String value) {
    return Arrays.stream(values()).filter(e -> Objects.equals(e.code, value)).findFirst();
  }

  public static boolean isSchoolAddressLabelReport(String reportType) {
    return ADDRESS_LABEL_SCHL.getCode().equals(reportType) || ADDRESS_LABEL_PSI.getCode().equals(reportType);
  }
  public static boolean isDistrictAddressLabelReport(String reportType) {
    return ADDRESS_LABEL_YE.getCode().equals(reportType) ;
  }
}
