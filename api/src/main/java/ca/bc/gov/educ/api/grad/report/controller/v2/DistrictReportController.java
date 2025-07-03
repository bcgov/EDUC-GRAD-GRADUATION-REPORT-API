package ca.bc.gov.educ.api.grad.report.controller.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.DistrictReport;
import ca.bc.gov.educ.api.grad.report.service.v2.DistrictReportService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import ca.bc.gov.educ.api.grad.report.util.PermissionsConstants;
import ca.bc.gov.educ.api.grad.report.util.ResponseHelper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(EducGradReportApiConstants.DISTRICT_REPORTS_ROOT_MAPPING)
@OpenAPIDefinition(info = @Info(title = "API for District Reports endpoints.", description = "This API is for reading and updating endpoints.", version = "2"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_REPORT_DATA","UPDATE_GRAD_STUDENT_REPORT_DATA"})})
public class DistrictReportController {
  DistrictReportService service;
  GradValidation validation;
  ResponseHelper response;

  @Autowired
  public DistrictReportController(DistrictReportService service, GradValidation validation, ResponseHelper response) {
    this.service = service;
    this.validation = validation;
    this.response = response;
  }

  @GetMapping(EducGradReportApiConstants.SEARCH_MAPPING)
  @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Search District Reports", description = "Search for district reports with optional filters", tags = {"Reports"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<List<DistrictReport>> searchDistrictReports(
          @RequestParam(value = "districtId", required = false) UUID districtId,
          @RequestParam(value = "reportTypeCode", required = false) String reportTypeCode,
          @RequestParam(value = "isLight", defaultValue = "false") boolean isLight) {
    log.debug("searchDistrictReports: ");
    List<DistrictReport> reports = service.searchDistrictReports(districtId, reportTypeCode, isLight);
    return ResponseEntity.ok(reports);
  }

  @GetMapping()
  @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Read district report data by district id and report type code", description = "Read district report data by district id and report type code", tags = {"Reports"})
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
  public ResponseEntity<InputStreamResource> getDistrictReportByDistrictIdAndReportType(
          @RequestParam(value = "districtId") UUID districtId,
          @RequestParam(value = "reportTypeCode") String reportTypeCode) {
    log.debug("getDistrictReportByType v2: ");
    var stream = service.getDistrictReportByDistrictIdAndReportType(districtId, reportTypeCode);
    return ResponseEntity.ok(stream);
  }

  @PostMapping()
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Save District Reports", description = "Save District Reports", tags = { "Reports" })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public DistrictReport saveDistrictReport(@RequestBody DistrictReport districtReports) {
    log.debug("Save {} District Report for {}",districtReports.getReportTypeCode(),districtReports.getDistrictId());
    validation.requiredField(districtReports.getDistrictId(), "District Id");
    return service.saveDistrictReports(districtReports);
  }

  @DeleteMapping(EducGradReportApiConstants.DELETE_DISTRICT_REPORT)
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Delete district report: ", description = "Delete a specific district report by districtId and reportTypeCode", tags = {"Credential"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Deleted successfully"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<Void> deleteDistrictReport(@PathVariable @NotNull UUID districtId, @PathVariable @NotNull String reportTypeCode) {
    log.debug("deleteDistrictReport: ");
    service.deleteDistrictReport(districtId, StringUtils.trim(reportTypeCode));
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Delete district reports by type", description = "Delete all district reports with the specified reportTypeCode", tags = {"Credential"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Deleted successfully"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<Void> deleteDistrictReportsByType(@RequestParam @NotNull String reportTypeCode) {
    log.debug("deleteDistrictReportsByType: ");
    service.deleteAllDistrictReportsByType(StringUtils.trim(reportTypeCode));
    return ResponseEntity.noContent().build();
  }
}
