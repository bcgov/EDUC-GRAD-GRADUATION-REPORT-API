package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.service.CommonService;
import ca.bc.gov.educ.api.grad.report.util.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@CrossOrigin
@RestController
@RequestMapping(EducGradReportApiConstants.GRAD_REPORT_API_ROOT_MAPPING)
@OpenAPIDefinition(info = @Info(title = "API for Common endpoints.", description = "This API is for Reading Common endpoints.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_UNGRAD_REASONS_DATA","READ_GRAD_STUDENT_CAREER_DATA"})})
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    private static final String BEARER = "Bearer ";
    @Autowired
    CommonService commonService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;
    
    @GetMapping(EducGradReportApiConstants.GET_STUDENT_CERTIFICATE_BY_CERTIFICATE_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Check if Certificate type is valid", description = "Check if Certificate Type is valid", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> getStudentCertificate(@PathVariable String certificateTypeCode) {
    	logger.debug("getStudentCertifcate : ");
        return response.GET(commonService.getStudentCertificate(certificateTypeCode));
    }
    
    @GetMapping(EducGradReportApiConstants.GET_STUDENT_REPORT_BY_REPORT_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Check if Report type is valid", description = "Check if Report Type is valid", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> getStudentReport(@PathVariable String reportTypeCode) { 
    	logger.debug("getStudentReport : ");
        return response.GET(commonService.getStudentReport(reportTypeCode));
    }
    
    
    @PostMapping (EducGradReportApiConstants.STUDENT_REPORT)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Save Student Reports", description = "Save Student Reports", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<GradStudentReports>> saveStudentReport(@RequestBody GradStudentReports gradStudentReports,@RequestParam(value = "isGraduated", required = false, defaultValue = "false") boolean isGraduated) {
        logger.debug("Save student Grad Report for Student ID: {}",gradStudentReports.getStudentID());
        validation.requiredField(gradStudentReports.getStudentID(), "Student ID");
        return response.UPDATED(commonService.saveGradStudentReports(gradStudentReports,isGraduated));
    }
    
    @GetMapping(EducGradReportApiConstants.STUDENT_REPORT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Reports by Student ID and Report Type", description = "Read Student Reports by Student ID and Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentReportByType(
    		@RequestParam(value = "studentID") String studentID,
    		@RequestParam(value = "reportType") String reportType,
    		@RequestParam(value = "documentStatusCode") String documentStatusCode) {
    	logger.debug("getStudentReportByType : ");
    	return commonService.getStudentReportByType(UUID.fromString(studentID),reportType,documentStatusCode);
    }

    @DeleteMapping(EducGradReportApiConstants.STUDENT_REPORT_BY_STUDENTID)
    @PreAuthorize(PermissionsConstants.DELETE_STUDENT_REPORT)
    @Operation(summary = "Delete Student Reports by Student ID and Report Type", description = "Delete Student Reports by Student ID and Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Integer> deleteStudentReportByType(
            @RequestParam(value = "reportType") String reportType,
            @PathVariable UUID studentID) {
        logger.debug("deleteStudentReportByType");
        return response.GET(commonService.deleteStudentReports(studentID, reportType));
    }

    @PostMapping(EducGradReportApiConstants.STUDENT_REPORTS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Reports by Student ID and Report Type", description = "Read Student Reports by Student ID and Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Long> processStudentReports(
            @RequestParam(value = "reportTypeCode") String reportTypeCode,
            @RequestBody List<UUID> studentIDs) {
        logger.debug("processStudentReports : ");
        return response.GET(commonService.processStudentReports(studentIDs, reportTypeCode));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_CERTIFICATES)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read Student Certificates by Student ID", description = "Read Student Certificates by Student ID", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<GradStudentCertificates> getStudentCertificates(
            @RequestParam(value = "studentID") String studentID) {
        logger.debug("getStudentCertificates : ");
        return commonService.getAllStudentCertificateList(UUID.fromString(studentID));
    }

    @GetMapping(EducGradReportApiConstants.CHECK_SCCP_CERTIFICATE_EXISTS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Check if SCCP Student Certificate exists or not", description = "Check if SCCP Student Certificate exists or not", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> existsStudentCertificateForSCCP(@RequestParam(value = "studentID") String studentID) {
        logger.debug("existsStudentCertificateForSCCP : ");
        return response.GET(commonService.checkStudentCertificateExistsForSCCP(UUID.fromString(studentID)));
    }
    
    @PostMapping (EducGradReportApiConstants.STUDENT_CERTIFICATE)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Save Student Certificate", description = "Save Student Certificate", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<GradStudentCertificates>> saveStudentCertificate(@RequestBody GradStudentCertificates gradStudentCertificates) {
        logger.debug("Save student Grad Certificate for PEN: {}", gradStudentCertificates.getPen());
        validation.requiredField(gradStudentCertificates.getPen(), "Pen");
        return response.UPDATED(commonService.saveGradCertificates(gradStudentCertificates));
    }

    @PostMapping (EducGradReportApiConstants.STUDENT_TRANSCRIPT)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Save Student Transcript", description = "Save Student Transcript", tags = { "Transcript" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<GradStudentTranscripts>> saveStudentTranscript(@RequestBody GradStudentTranscripts gradStudentTranscripts,@RequestParam(value = "isGraduated", required = false, defaultValue = "false") boolean isGraduated) {
        logger.debug("Save student Grad Transcript for Student ID: {}", gradStudentTranscripts.getStudentID());
        validation.requiredField(gradStudentTranscripts.getStudentID(), "Student ID");
        return response.UPDATED(commonService.saveGradTranscripts(gradStudentTranscripts,isGraduated));
    }
    
    @GetMapping(EducGradReportApiConstants.STUDENT_CERTIFICATE)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read Student Certificate by Student ID and Certificate Type", description = "Read Student Certificate by Student ID and Certificate Type", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentCertificateByType(
    		@RequestParam(value = "studentID") String studentID,
    		@RequestParam(value = "certificateType") String certificateType,
    		@RequestParam(value = "documentStatusCode") String documentStatusCode) {
    	logger.debug("getStudentCertificateByType :");
    	return commonService.getStudentCertificateByType(UUID.fromString(studentID),certificateType,documentStatusCode);
    }
    @GetMapping(EducGradReportApiConstants.STUDENT_CERTIFICATE_BY_STUDENTID)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All  Student Certificates by Student ID", description = "Read All Student Certificates by Student ID", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentCertificates>> getAllStudentCertificateList(@PathVariable String studentID) { 
    	logger.debug("getAllStudentCertificateList : ");
        return response.GET(commonService.getAllStudentCertificateList(UUID.fromString(studentID)));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_BY_STUDENTID)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All  Student Transcripts by Student ID", description = "Read All Student Transcripts by Student ID", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentTranscripts>> getAllStudentTranscriptList(@PathVariable String studentID) {
        logger.debug("getAllStudentTranscriptList : ");
        return response.GET(commonService.getAllStudentTranscriptList(UUID.fromString(studentID)));
    }
    
    @GetMapping(EducGradReportApiConstants.STUDENT_REPORTS_BY_STUDENTID)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All  Student Reports by Student ID", description = "Read All Student Reports by Student ID", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentReports>> getAllStudentReportsList(@PathVariable String studentID) { 
    	logger.debug("getAllStudentReportsList : ");
        return response.GET(commonService.getAllStudentReportList(UUID.fromString(studentID)));
    }
    
    @DeleteMapping(EducGradReportApiConstants.DELETE_ACHIEVEMENTS_BY_STUDENTID)
    @PreAuthorize(PermissionsConstants.DELETE_STUDENT_ACHIEVEMENT_DATA)
    @Operation(summary = "Delete All  Student Achievements by Student ID", description = "Delete All Student Certificates by Student ID", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO_CONTENT")})
    public ResponseEntity<Void> deleteAllStudentAchievements(@PathVariable String studentID) { 
    	logger.debug("deleteAllStudentAchievements : ");
        return response.DELETE(commonService.deleteAllStudentAchievement(UUID.fromString(studentID)));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_CERTIFICATE_BY_DIST_DATE_N_STATUS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All  Student Certificates For Distribution", description = "Read All Student Certificates For Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getAllStudentCertificateDistribution() {
        logger.debug("getAllStudentCertificateDistribution : ");
        return response.GET(commonService.getAllStudentCertificateDistributionList());
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_FOR_SCHOOL_YEAREND_REPORT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Get List of students for school year end reports", description = "Get List of students for school year end reports", tags = { "School Year End Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ReportGradStudentData>> getSchoolYearEndReportGradStudentData() {
        logger.debug("getAllStudentSchoolYearEndDistribution : ");
        return response.GET(commonService.getSchoolYearEndReportGradStudentData());
    }

    @PostMapping(EducGradReportApiConstants.STUDENT_FOR_SCHOOL_YEAREND_REPORT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Get List of students for school year end reports", description = "Get List of students for school year end reports", tags = { "School Year End Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ReportGradStudentData>> getSchoolYearEndReportGradStudentData(@RequestBody List<String> schools) {
        logger.debug("getAllStudentSchoolYearEndDistribution :");
        return response.GET(commonService.getSchoolYearEndReportGradStudentData(schools));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_FOR_SCHOOL_REPORT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Get List of students for school year end reports", description = "Get List of students for school year end reports", tags = { "School Year End Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ReportGradStudentData>> getSchoolReportGradStudentData() {
        logger.debug("getAllStudentSchoolYearEndDistribution : ");
        return response.GET(commonService.getSchoolReportGradStudentData());
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS_YEARLY)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All Student Transcripts for Distribution", description = "Read All Student Transcripts for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getAllStudentTranscriptYearlyDistribution(
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getAllStudentTranscriptYearlyDistribution : ");
        return response.GET(commonService.getAllStudentTranscriptYearlyDistributionList(accessToken.replace(BEARER, "")));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All Student Transcripts for Distribution", description = "Read All Student Transcripts for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getAllStudentTranscriptDistribution() {
        logger.debug("getAllStudentTranscriptDistribution : ");
        return response.GET(commonService.getAllStudentTranscriptDistributionList());
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_N_REPORTS_POSTING)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All Student Transcripts for Distribution", description = "Read All Student Transcripts for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<SchoolStudentCredentialDistribution>> getAllStudentTranscriptAndReportsPosting() {
        logger.debug("getAllStudentTranscriptAndReportsPosting : ");
        return response.GET(commonService.getAllStudentTranscriptAndReportsPosting());
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Certificate by Student ID and Certificate Type", description = "Read Student Certificate by Student ID and Certificate Type", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentTranscriptByType(
            @RequestParam(value = "studentID") String studentID,
            @RequestParam(value = "transcriptType") String transcriptType,
            @RequestParam(value = "documentStatusCode") String documentStatusCode) {
        logger.debug("getStudentTranscriptByType :");
        return commonService.getStudentTranscriptByType(UUID.fromString(studentID),transcriptType,documentStatusCode);
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_PSI)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Certificate by Student ID and Certificate Type", description = "Read Student Certificate by Student ID and Certificate Type", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentTranscriptByTypeID(@PathVariable String studentID) {
        logger.debug("getStudentTranscriptByType :");
        return commonService.getStudentTranscriptByStudentID(UUID.fromString(studentID));
    }

    @GetMapping(EducGradReportApiConstants.UPDATE_STUDENT_CREDENTIAL)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Update Student Credential", description = "Update Student Credential", tags = { "Credential" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> updateStudentCredential(@RequestParam String studentID, @RequestParam String credentialTypeCode, @RequestParam String paperType, @RequestParam String documentStatusCode, @RequestParam(required = false) String activityCode) {
        logger.debug("updateStudentCredential");
        return response.GET(commonService.updateStudentCredential(UUID.fromString(studentID),credentialTypeCode,paperType,documentStatusCode,activityCode));
    }

    @GetMapping(EducGradReportApiConstants.UPDATE_STUDENT_CREDENTIAL_POSTING)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Update Student Credential", description = "Update Student Credential", tags = { "Credential" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> updateStudentCredentialPosting(@RequestParam String studentID,@RequestParam String credentialTypeCode) {
        logger.debug("updateStudentCredential");
        return response.GET(commonService.updateStudentCredentialPosting(UUID.fromString(studentID),credentialTypeCode));
    }

    @PostMapping(EducGradReportApiConstants.USER_REQUEST_DIS_RUN)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All Student Transcripts/Certificates for User Req Distribution", description = "Read All Student Credentials for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getStudentCredentialsForUserRequestDisRun(
            @PathVariable String credentialType, @RequestBody StudentSearchRequest studentSearchRequest,
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getStudentCredentialsForUserRequestDisRun : ");
        boolean isPenNumberSearch = studentSearchRequest.getPens() != null && !studentSearchRequest.getPens().isEmpty()
                && !studentSearchRequest.getPens().stream().filter(StringUtils::isNotBlank).toList().isEmpty();
        boolean onlyWithNullDistributionDate = !isPenNumberSearch && studentSearchRequest.getGradDateFrom() == null && studentSearchRequest.getGradDateTo() == null && !StringUtils.equalsAnyIgnoreCase(credentialType, "OT", "RT");
        return response.GET(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,studentSearchRequest,onlyWithNullDistributionDate,accessToken.replace(BEARER, "")));
    }

    @PostMapping(EducGradReportApiConstants.USER_REQUEST_DIS_RUN_WITH_NULL_DISTRIBUTION_DATE)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All Student Transcripts/Certificates with Null Distribution Date for User Req Distribution", description = "Read All Student Credentials with Null Distribution Date for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getStudentCredentialsForUserRequestDisRunWithNullDistributionDate(
            @PathVariable String credentialType, @RequestBody StudentSearchRequest studentSearchRequest,
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getStudentCredentialsForUserRequestDisRunWithNullDistributionDate : ");
        boolean isPenNumberSearch = studentSearchRequest.getPens()!= null && !studentSearchRequest.getPens().isEmpty()
                && !studentSearchRequest.getPens().stream().filter(StringUtils::isNotBlank).toList().isEmpty();
        return response.GET(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,studentSearchRequest,!isPenNumberSearch,accessToken.replace(BEARER, "")));
    }

    @DeleteMapping(EducGradReportApiConstants.ARCH_ACHIEVEMENTS_BY_STUDENTID)
    @PreAuthorize(PermissionsConstants.DELETE_STUDENT_ACHIEVEMENT_DATA)
    @Operation(summary = "Archive All  Student Achievements by Student ID", description = "Archive All Student Certificates by Student ID", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO_CONTENT")})
    public ResponseEntity<Void> archiveAllStudentAchievements(@PathVariable String studentID) {
        logger.debug("deleteAllStudentAchievements : ");
        return response.DELETE(commonService.archiveAllStudentAchievements(UUID.fromString(studentID)));
    }

    @PostMapping (EducGradReportApiConstants.SCHOOL_REPORT)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Save Student Reports", description = "Save Student Reports", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<SchoolReports>> saveSchoolReport(@RequestBody SchoolReports schoolReports) {
        logger.debug("Save {} School Report for {}",schoolReports.getReportTypeCode(),schoolReports.getSchoolOfRecord());
        validation.requiredField(schoolReports.getSchoolOfRecord(), "School of Record");
        return response.UPDATED(commonService.saveSchoolReports(schoolReports));
    }

    @GetMapping(EducGradReportApiConstants.SCHOOL_REPORTS_BY_MINCODE)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All  School Reports by Mincode", description = "Read All School Reports by Mincode", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<SchoolReports>> getAllSchoolReportsList(@PathVariable String mincode) {
        logger.debug("getAllSchoolReportsList : ");
        return response.GET(commonService.getAllSchoolReportListByMincode(mincode));
    }

    @GetMapping(EducGradReportApiConstants.SCHOOL_REPORTS_BY_REPORT_TYPE)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All School Reports by Report Type", description = "Read All School Reports by Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<SchoolReports>> getSchoolReportsListByReportType(
            @PathVariable String reportType,
            @RequestParam(value = "mincode", required = false) String mincode,
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getSchoolReportsListByReportType : ");
        return response.GET(commonService.getAllSchoolReportListByReportType(reportType, mincode));
    }

    @GetMapping(EducGradReportApiConstants.SCHOOL_REPORT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Reports by Student ID and Report Type", description = "Read Student Reports by Student ID and Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getSchoolReportByMincodeAndReportType(
            @RequestParam(value = "mincode") String mincode,
            @RequestParam(value = "reportType") String reportType) {
        logger.debug("getSchoolReportByType : ");
        return commonService.getSchoolReportByMincodeAndReportType(mincode,reportType);
    }

    @GetMapping(EducGradReportApiConstants.UPDATE_SCHOOL_REPORTS)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Update Student Credential", description = "Update Student Credential", tags = { "Credential" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> updateSchoolReport(@RequestParam String mincode,@RequestParam String reportTypeCode) {
        logger.debug("updateSchoolReport ");
        return response.GET(commonService.updateSchoolReports(mincode,reportTypeCode));
    }

    @DeleteMapping(EducGradReportApiConstants.UPDATE_SCHOOL_REPORTS)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Update Student Credential", description = "Update Student Credential", tags = { "Credential" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> deleteSchoolReport(@RequestParam(required = false) String mincode, @RequestParam String reportTypeCode) {
        logger.debug("updateSchoolReport ");
        return response.GET(commonService.deleteSchoolReports(mincode, StringUtils.trim(reportTypeCode)));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_CREDENTIAL_BUSINESS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Credentials by Student ID and Type of Credential", description = "Read Student Credentials by Student ID and Type of Credential", tags = { "Credentials" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentCredentialByType(@PathVariable String studentID,@PathVariable String type) {
        logger.debug("getStudentCredentialByType :");
        return commonService.getStudentCredentialByType(UUID.fromString(studentID),type);
    }

    @PostMapping (EducGradReportApiConstants.REPORT_COUNT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Get Reports Count by mincode and status", description = "Get Students Count by mincode and status", tags = { "Business" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Integer> getReportsCount(@RequestParam String reportType, @RequestBody List<String> reportContainerIds) {
        if(StringUtils.containsAnyIgnoreCase(reportType, "ACHV")) {
            return response.GET(commonService.countByStudentGuidsAndReportType(reportContainerIds, reportType));
        } else {
            return response.GET(commonService.countBySchoolOfRecordsAndReportType(reportContainerIds, reportType));
        }
    }

    @PostMapping (EducGradReportApiConstants.REPORT_ARCHIVE)
    @PreAuthorize(PermissionsConstants.ARCHIVE_SCHOOL_REPORT)
    @Operation(summary = "Archive Reports", description = "Archive Reports", tags = { "Business" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Integer> archiveReports(@RequestParam long batchId, @RequestParam String reportType, @RequestBody List<String> schoolOfRecords) {
        logger.debug("Archive Reports for batch {}", batchId);
        return response.GET(commonService.archiveSchoolReports(batchId, schoolOfRecords, reportType));
    }

    @DeleteMapping (EducGradReportApiConstants.REPORT_DELETE)
    @PreAuthorize(PermissionsConstants.DELETE_STUDENT_REPORT)
    @Operation(summary = "Delete Reports", description = "Delete Reports", tags = { "Business" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Integer> deleteReports(@RequestParam long batchId, @RequestParam String reportType, @RequestBody List<UUID> studentGuids) {
        logger.debug("Delete Reports for batch {}", batchId);
        return response.GET(commonService.deleteStudentReports(studentGuids, reportType));
    }
}
