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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(EducGradCommonApiConstants.GRAD_COMMON_API_ROOT_MAPPING)
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
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_UNGRAD_REASONS_DATA)
    @Operation(summary = "Find Student Ungrad Reasons by Student ID", description = "Get Student Ungrad Reasons By Student ID", tags = { "Ungrad Reasons" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentUngradReasons>> getAllStudentUngradReasonsList(@PathVariable String studentID) { 
    	logger.debug("getAllStudentUngradReasonsList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return response.GET(commonService.getAllStudentUngradReasonsList(UUID.fromString(studentID),accessToken));
    }
    
    @PostMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_UNGRAD_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_GRAD_STUDENT_UNGRAD_REASONS_DATA)
    @Operation(summary = "Create an Ungrad Reasons", description = "Create an Ungrad Reasons", tags = { "Ungrad Reasons" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradStudentUngradReasons>> createGradStudentUngradReason(@PathVariable String studentID, @Valid @RequestBody GradStudentUngradReasons gradStudentUngradReasons) {
    	logger.debug("createGradUngradReasons : ");
    	validation.requiredField(gradStudentUngradReasons.getStudentID(), "Student ID");
    	validation.requiredField(gradStudentUngradReasons.getUngradReasonCode(), "Ungrad Reason Code");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return response.CREATED(commonService.createGradStudentUngradReasons(gradStudentUngradReasons,accessToken));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_UNGRAD_BY_REASON_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_UNGRAD_REASONS_DATA)
    @Operation(summary = "Check if Ungrad Reasons is valid", description = "Check if ungrad reason is valid", tags = { "Ungrad Reasons" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> getStudentUngradReasons(@PathVariable String reasonCode) { 
    	logger.debug("getStudentUngradReasons : ");
        return response.GET(commonService.getStudentUngradReasons(reasonCode));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_CAREER_PROGRAM_BY_CAREER_PROGRAM_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_CAREER_DATA)
    @Operation(summary = "Check if Career Program is valid", description = "Check if Career Program is valid", tags = { "Career Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> getStudentCareerProgram(@PathVariable String cpCode) { 
    	logger.debug("getStudentCareerProgram : ");
        return response.GET(commonService.getStudentCareerProgram(cpCode));
    }
    
   
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_CAREER_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_CAREER_DATA)
    @Operation(summary = "Find Student Career Program by Pen", description = "Find Student Career Program by Pen", tags = { "Career Programs" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentCareerProgram>> getAllStudentCareerProgramsList(@PathVariable String pen) { 
    	logger.debug("getAllStudentCareerProgramsList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return response.GET(commonService.getAllGradStudentCareerProgramList(pen,accessToken));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_CERTIFICATE_BY_CERTIFICATE_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Check if Certificate type is valid", description = "Check if Certificate Type is valid", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> getStudentCertificate(@PathVariable String certificateTypeCode) {
    	logger.debug("getStudentCertifcate : ");
        return response.GET(commonService.getStudentCertificate(certificateTypeCode));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_STUDENT_REPORT_BY_REPORT_CODE_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Check if Report type is valid", description = "Check if Report Type is valid", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Boolean> getStudentReport(@PathVariable String reportTypeCode) { 
    	logger.debug("getStudentReport : ");
        return response.GET(commonService.getStudentReport(reportTypeCode));
    }
    
    
    @PostMapping (EducGradCommonApiConstants.STUDENT_REPORT)
    @PreAuthorize(PermissionsContants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Save Student Reports", description = "Save Student Reports", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<GradStudentReports>> saveStudentReport(@RequestBody GradStudentReports gradStudentReports) {
        logger.debug("Save student Grad Report for PEN: " + gradStudentReports.getPen());
        validation.requiredField(gradStudentReports.getPen(), "Pen");
        return response.UPDATED(commonService.saveGradReports(gradStudentReports));
    }
    
    @GetMapping(EducGradCommonApiConstants.STUDENT_REPORT)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Read Student Reports by Student ID and Report Type", description = "Read Student Reports by Student ID and Report Type", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentReportByType(
    		@RequestParam(value = "studentID", required = true) String studentID,
    		@RequestParam(value = "reportType", required = true) String reportType) { 
    	logger.debug("getStudentReportByType : ");
    	return commonService.getStudentReportByType(UUID.fromString(studentID),reportType);
    }
    
    @PostMapping (EducGradCommonApiConstants.STUDENT_CERTIFICATE)
    @PreAuthorize(PermissionsContants.UPDATE_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Save Student Certificate", description = "Save Student Certificate", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<GradStudentCertificates>> saveStudentCertificate(@RequestBody GradStudentCertificates gradStudentCertificates) {
        logger.debug("Save student Grad Certificate for PEN: " + gradStudentCertificates.getPen());
        validation.requiredField(gradStudentCertificates.getPen(), "Pen");
        return response.UPDATED(commonService.saveGradCertificates(gradStudentCertificates));
    }
    
    @GetMapping(EducGradCommonApiConstants.STUDENT_CERTIFICATE)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read Student Certificate by Student ID and Certificate Type", description = "Read Student Certificate by Student ID and Certificate Type", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<InputStreamResource> getStudentCertificateByType(
    		@RequestParam(value = "studentID", required = true) String studentID,
    		@RequestParam(value = "certificateType", required = true) String certificateType) { 
    	logger.debug("getStudentCertificateByType :");
    	return commonService.getStudentCertificateByType(UUID.fromString(studentID),certificateType);
    }
    
    @GetMapping(EducGradCommonApiConstants.STUDENT_CERTIFICATE_BY_STUDENTID)
    @PreAuthorize(PermissionsContants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Read All  Student Certificates by Student ID", description = "Read All Student Certificates by Student ID", tags = { "Certificates" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentCertificates>> getAllStudentCertificateList(@PathVariable String studentID) { 
    	logger.debug("getAllStudentCertificateList : ");
    	OAuth2AuthenticationDetails auth = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails(); 
    	String accessToken = auth.getTokenValue();
        return response.GET(commonService.getAllStudentCertificateList(UUID.fromString(studentID),accessToken));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_ALGORITHM_RULES_MAIN_PROGRAM)
    @PreAuthorize(PermissionsContants.READ_GRAD_ALGORITHM_RULES)
    @Operation(summary = "Read All  Grad Algorithm Rules by Program Code", description = "Read All  Grad Algorithm Rules by Program Code which are active", tags = { "Algorithm" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradAlgorithmRules>> getAlgorithmRulesList(@PathVariable String programCode) { 
    	logger.debug("getAlgorithmRulesList : ");
        return response.GET(commonService.getAlgorithmRulesList(programCode));
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_ALGORITHM_RULES_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_ALGORITHM_RULES)
    @Operation(summary = "Read All  Grad Algorithm Rules", description = "Read All  Grad Algorithm Rules", tags = { "Algorithm" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradAlgorithmRules>> getAllAlgorithmRulesList() { 
    	logger.debug("getAllAlgorithmRulesList : ");
        return response.GET(commonService.getAllAlgorithmRulesList());
    }
    
    @GetMapping(EducGradCommonApiConstants.GET_ALL_STUDENT_NOTES_MAPPING)
    @PreAuthorize(PermissionsContants.READ_GRAD_STUDENT_NOTES_DATA)
    @Operation(summary = "Find Student Notes by Pen", description = "Get Student Notes By Pen", tags = { "Student Notes" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<StudentNote>> getAllStudentNotes(@PathVariable String pen) { 
    	logger.debug("getAllStudentNotes : ");
        return response.GET(commonService.getAllStudentNotes(pen));
    }
    
    @PostMapping (EducGradCommonApiConstants.STUDENT_NOTES_MAPPING)
    @PreAuthorize(PermissionsContants.CREATE_OR_UPDATE_GRAD_STUDENT_NOTES_DATA)
    @Operation(summary = "Create Student Notes", description = "Create Student Notes", tags = { "Student Notes" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<StudentNote>> saveStudentNotes(@RequestBody StudentNote studentNote) {
        logger.debug("Create student Grad Note for PEN: " + studentNote.getPen());
        validation.requiredField(studentNote.getPen(), "Pen");
        return response.UPDATED(commonService.saveStudentNote(studentNote));
    }
    
    @DeleteMapping(EducGradCommonApiConstants.STUDENT_NOTES_DELETE_MAPPING)
    @PreAuthorize(PermissionsContants.DELETE_GRAD_STUDENT_NOTES_DATA)
    @Operation(summary = "Delete a note", description = "Delete a note", tags = { "Student Notes" })
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "NO CONTENT"), @ApiResponse(responseCode = "404", description = "NOT FOUND.")})
    public ResponseEntity<Void> deleteNotes(@Valid @PathVariable String noteID) { 
    	logger.debug("deleteNotes : ");
    	validation.requiredField(noteID, "Note ID");
    	if(validation.hasErrors()) {
    		validation.stopOnErrors();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
        return response.DELETE(commonService.deleteNote(UUID.fromString(noteID)));
    }
   
}
