package ca.bc.gov.educ.api.grad.report.controller.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.SchoolReport;
import ca.bc.gov.educ.api.grad.report.service.v2.SchoolReportService;
import ca.bc.gov.educ.api.grad.report.util.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(EducGradReportApiConstants.SCHOOL_REPORTS_ROOT_MAPPING)
@OpenAPIDefinition(info = @Info(title = "API for School Reports endpoints.", description = "This API is for reading and updating endpoints.", version = "2"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_REPORT_DATA","UPDATE_GRAD_STUDENT_REPORT_DATA"})})
public class SchoolReportController {
  private static final Logger logger = LoggerFactory.getLogger(SchoolReportController.class);

  SchoolReportService service;
  GradValidation validation;
  ResponseHelper response;

  @Autowired
  public SchoolReportController(SchoolReportService service, GradValidation validation, ResponseHelper response) {
      this.service = service;
      this.validation = validation;
      this.response = response;
  }

  @GetMapping(EducGradReportApiConstants.SEARCH_MAPPING)
  @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Search School Reports", description = "Search for school reports with optional filters", tags = {"Reports"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<List<SchoolReport>> searchSchoolReports(
          @RequestParam(value = "schoolOfRecordId", required = false) UUID schoolOfRecordId,
          @RequestParam(value = "reportTypeCode", required = false) String reportTypeCode,
          @RequestParam(value = "isLight", defaultValue = "false") boolean isLight) {
    logger.debug("searchSchoolReports: ");
    List<SchoolReport> reports = service.searchSchoolReports(schoolOfRecordId, reportTypeCode, isLight);
    return ResponseEntity.ok(reports);
  }

  @GetMapping()
  @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Read school report data by school id and report type code", description = "Read school report data by school id and report type code", tags = {"Reports"})
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
  public ResponseEntity<InputStreamResource> getSchoolReportBySchoolOfRecordIdAndReportType(
          @RequestParam(value = "schoolOfRecordId") UUID schoolOfRecordId,
          @RequestParam(value = "reportTypeCode") String reportTypeCode) {
    logger.debug("getSchoolReportByType v2: ");
    var stream = service.getSchoolReportBySchoolOfRecordIdAndReportType(schoolOfRecordId, reportTypeCode);
    return ResponseEntity.ok(stream);
  }

  @PostMapping(EducGradReportApiConstants.UPDATE_SCHOOL_REPORTS_UPDATE_DETAILS)
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Update school report: ", description = "Update school report by setting updateUser and updateDate to null", tags = {"Credential"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "No Content"),
          @ApiResponse(responseCode = "404", description = "Not Found"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<Void> updateSchoolReport(@PathVariable @NotNull UUID schoolOfRecordId, @PathVariable @NotNull String reportTypeCode) {
    logger.debug("updateSchoolReport v2: ");
    service.updateSchoolReports(schoolOfRecordId, StringUtils.trim(reportTypeCode));
    return ResponseEntity.noContent().build();
  }

  @PostMapping ()
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Save Student Reports", description = "Save Student Reports", tags = { "Reports" })
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public SchoolReport saveSchoolReport(@RequestBody SchoolReport schoolReports) {
    logger.debug("Save {} School Report for {}",schoolReports.getReportTypeCode(),schoolReports.getSchoolOfRecordId());
    validation.requiredField(schoolReports.getSchoolOfRecordId(), "School of Record Id");
    return service.saveSchoolReports(schoolReports);
  }

  @DeleteMapping(EducGradReportApiConstants.DELETE_SCHOOL_REPORT)
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Delete school report: ", description = "Delete a specific school report by schoolOfRecordId and reportTypeCode", tags = {"Credential"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Deleted successfully"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<Void> deleteSchoolReport(@PathVariable @NotNull UUID schoolOfRecordId, @PathVariable @NotNull String reportTypeCode) {
    logger.debug("deleteSchoolReport: ");
    service.deleteSchoolReport(schoolOfRecordId, StringUtils.trim(reportTypeCode));
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
  @Operation(summary = "Delete school reports by type", description = "Delete all school reports with the specified reportTypeCode", tags = {"Credential"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Deleted successfully"),
          @ApiResponse(responseCode = "400", description = "Bad request")})
  public ResponseEntity<Void> deleteSchoolReportsByType(@RequestParam @NotNull String reportTypeCode) {
    logger.debug("deleteSchoolReportsByType: ");
    service.deleteAllSchoolReportsByType(StringUtils.trim(reportTypeCode));
    return ResponseEntity.noContent().build();
  }
}
