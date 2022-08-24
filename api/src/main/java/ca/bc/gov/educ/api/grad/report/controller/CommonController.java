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
        return response.UPDATED(commonService.saveGradReports(gradStudentReports,isGraduated));
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

    @GetMapping(EducGradReportApiConstants.STUDENT_CERTIFICATES)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read Student Certificates by Student ID", description = "Read Student Certificates by Student ID", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<GradStudentCertificates> getStudentCertificates(
            @RequestParam(value = "studentID") String studentID) {
        logger.debug("getStudentCertificates : ");
        return commonService.getAllStudentCertificateList(UUID.fromString(studentID));
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
        return response.DELETE(commonService.getAllStudentAchievement(UUID.fromString(studentID)));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_CERTIFICATE_BY_DIST_DATE_N_STATUS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All  Student Certificates For Distribution", description = "Read All Student Certificates For Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getAllStudentCertificateDistribution() {
        logger.debug("getAllStudentCertificateDistribution : ");
        return response.GET(commonService.getAllStudentCertificateDistributionList());
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
    public ResponseEntity<Boolean> updateStudentCredential(@RequestParam String studentID,@RequestParam String credentialTypeCode,@RequestParam String paperType,@RequestParam String documentStatusCode) {
        logger.debug("updateStudentCredential");
        return response.GET(commonService.updateStudentCredential(UUID.fromString(studentID),credentialTypeCode,paperType,documentStatusCode));
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
    @Operation(summary = "Read All Student Transcripts/Certificates for  User Req Distribution", description = "Read All Student Transcripts for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getStudentCredentialsForUserRequestDisRun(
            @PathVariable String credentialType, @RequestBody StudentSearchRequest studentSearchRequest,
            @RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getStudentCredentialsForUserRequestDisRun : ");
        return response.GET(commonService.getStudentCredentialsForUserRequestDisRun(credentialType,studentSearchRequest,accessToken.replace(BEARER, "")));
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
    public ResponseEntity<List<SchoolReports>> getAllSchoolReportsList(@PathVariable String mincode,@RequestHeader(name="Authorization") String accessToken) {
        logger.debug("getAllSchoolReportsList : ");
        return response.GET(commonService.getAllSchoolReportList(mincode, accessToken.replace(BEARER,"")));
    }

    @GetMapping(EducGradReportApiConstants.SCHOOL_REPORT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Reports by Student ID and Report Type", description = "Read Student Reports by Student ID and Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getSchoolReportByType(
            @RequestParam(value = "mincode") String mincode,
            @RequestParam(value = "reportType") String reportType) {
        logger.debug("getSchoolReportByType : ");
        return commonService.getSchoolReportByType(mincode,reportType);
    }

    @GetMapping(EducGradReportApiConstants.UPDATE_SCHOOL_REPORTS)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Update Student Credential", description = "Update Student Credential", tags = { "Credential" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> updateSchoolReport(@RequestParam String mincode,@RequestParam String reportTypeCode) {
        logger.debug("updateSchoolReport ");
        return response.GET(commonService.updateSchoolReports(mincode,reportTypeCode));
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_CREDENTIAL_BUSINESS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Credentials by Student ID and Type of Credential", description = "Read Student Credentials by Student ID and Type of Credential", tags = { "Credentials" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentCredentialByType(@PathVariable String studentID,@PathVariable String type) {
        logger.debug("getStudentCredentialByType :");
        return commonService.getStudentCredentialByType(UUID.fromString(studentID),type);
    }


}
