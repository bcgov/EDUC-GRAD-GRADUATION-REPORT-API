package ca.bc.gov.educ.api.grad.report.controller;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentTranscriptValidation;
import ca.bc.gov.educ.api.grad.report.service.StudentTranscriptValidationService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(EducGradReportApiConstants.GRAD_REPORT_API_ROOT_MAPPING)
@OpenAPIDefinition(info = @Info(title = "API for Common endpoints.", description = "This API is for Reading Common endpoints.", version = "1"), security = {@SecurityRequirement(name = "OAUTH2", scopes = {"READ_GRAD_STUDENT_UNGRAD_REASONS_DATA","READ_GRAD_STUDENT_CAREER_DATA"})})
public class ReportDataMaintenanceController {

    private static Logger logger = LoggerFactory.getLogger(ReportDataMaintenanceController.class);

    private static final String BEARER = "Bearer ";
    @Autowired
    StudentTranscriptValidationService studentTranscriptValidationService;
    
    @Autowired
	GradValidation validation;
    
    @Autowired
	ResponseHelper response;
    
    @GetMapping(EducGradReportApiConstants.STUDENT_TRANSCRIPT_VALIDATION)
    @PreAuthorize(PermissionsConstants.READ_GRADUATION_STUDENT_CERTIFICATES)
    @Operation(summary = "Get List of Student Transcripts for Validation", description = "Get List of Student Transcripts for Validation", tags = { "Transcripts" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradStudentTranscriptValidation>> getStudentTranscriptValidation(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
    	logger.debug("getStudentTranscriptValidation: {}/{}", pageNumber, pageSize);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return response.GET(studentTranscriptValidationService.getGradStudentTranscriptValidation(pageable));
    }

    @PostMapping (EducGradReportApiConstants.STUDENT_TRANSCRIPT_VALIDATION)
    @PreAuthorize(PermissionsConstants.UPDATE_GRADUATION_STUDENT_REPORTS)
    @Operation(summary = "Save Student Reports", description = "Save Student Reports", tags = { "Reports" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ApiResponseModel<GradStudentTranscriptValidation>> saveStudentTranscriptValidation(@RequestBody GradStudentTranscriptValidation gradStudentTranscriptValidation) {
        logger.debug("saveStudentTranscriptValidation for Student: {}/{}",gradStudentTranscriptValidation.getStudentTranscriptValidationKey());
        validation.requiredField(gradStudentTranscriptValidation.getStudentTranscriptValidationKey().getStudentID(), "Student ID");
        validation.requiredField(gradStudentTranscriptValidation.getStudentTranscriptValidationKey().getPen(), "Student Pen");
        validation.requiredField(gradStudentTranscriptValidation.getTranscriptTypeCode(), "Transcript Type Code");
        validation.requiredField(gradStudentTranscriptValidation.getDocumentStatusCode(), "Document Status Code");
        return response.CREATED(studentTranscriptValidationService.saveGradStudentTranscriptValidation(gradStudentTranscriptValidation));
    }
    
}

