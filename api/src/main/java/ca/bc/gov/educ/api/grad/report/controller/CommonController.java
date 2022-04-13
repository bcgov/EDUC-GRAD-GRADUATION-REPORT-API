package ca.bc.gov.educ.api.grad.report.controller;

import java.util.List;
import java.util.UUID;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentTranscripts;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentCredentialDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentCertificates;
import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.grad.report.service.CommonService;
import ca.bc.gov.educ.api.grad.report.util.ApiResponseModel;
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

@CrossOrigin
@RestController
@RequestMapping(EducGradReportApiConstants.GRAD_REPORT_API_ROOT_MAPPING)
@EnableResourceServer
@OpenAPIDefinition(info = @Info(title = "API for Common endpoints.", description = "This API is for Reading Common endpoints.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_UNGRAD_REASONS_DATA","READ_GRAD_STUDENT_CAREER_DATA"})})
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

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
    		@RequestParam(value = "studentID", required = true) String studentID,
    		@RequestParam(value = "reportType", required = true) String reportType,
    		@RequestParam(value = "documentStatusCode", required = true) String documentStatusCode) { 
    	logger.debug("getStudentReportByType : ");
    	return commonService.getStudentReportByType(UUID.fromString(studentID),reportType,documentStatusCode);
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
    		@RequestParam(value = "studentID", required = true) String studentID,
    		@RequestParam(value = "certificateType", required = true) String certificateType,
    		@RequestParam(value = "documentStatusCode", required = true) String documentStatusCode) { 
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

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_BY_DIST_DATE_N_STATUS)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read All Student Transcripts for Distribution", description = "Read All Student Transcripts for Distribution", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentCredentialDistribution>> getAllStudentTranscriptDistribution() {
        logger.debug("getAllStudentTranscriptDistribution : ");
        return response.GET(commonService.getAllStudentTranscriptDistributionList());
    }

    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Certificate by Student ID and Certificate Type", description = "Read Student Certificate by Student ID and Certificate Type", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentTranscriptByType(
            @RequestParam(value = "studentID", required = true) String studentID,
            @RequestParam(value = "transcriptType", required = true) String transcriptType,
            @RequestParam(value = "documentStatusCode", required = true) String documentStatusCode) {
        logger.debug("getStudentTranscriptByType :");
        return commonService.getStudentTranscriptByType(UUID.fromString(studentID),transcriptType,documentStatusCode);
    }

    @GetMapping(EducGradReportApiConstants.UPDATE_STUDENT_CREDENTIAL)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Update Student Credential", description = "Update Student Credential", tags = { "Credential" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> updateStudentCredential(@PathVariable String studentID,@PathVariable String credentialTypeCode,@PathVariable String paperType) {
        logger.info("updateStudentCredential : ");
        return response.GET(commonService.updateStudentCredential(UUID.fromString(studentID),credentialTypeCode,paperType));
    }
   
}
