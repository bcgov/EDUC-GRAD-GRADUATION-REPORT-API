package ca.bc.gov.educ.api.grad.report.controller;

import java.util.List;
import jakarta.validation.Valid;
import ca.bc.gov.educ.api.grad.report.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.bc.gov.educ.api.grad.report.service.CodeService;
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

@RestController
@RequestMapping(EducGradReportApiConstants.GRAD_REPORT_API_ROOT_MAPPING)
@CrossOrigin
@OpenAPIDefinition(info = @Info(title = "API for Code Tables Data.",
        description = "This API is for Reading Code Tables data.", version = "1"),
        security = {@SecurityRequirement(name = "OAUTH2",
                scopes = {"READ_GRAD_COUNTRY_CODE_DATA",
                        "READ_GRAD_PROVINCE_CODE_DATA",
                        "READ_GRAD_PROGRAM_CODE_DATA",
                        "READ_GRAD_UNGRAD_CODE_DATA",
                        "READ_GRAD_CERTIFICATE_CODE_DATA",
                        "READ_GRAD_MESSEGING_CODE_DATA",
                        "READ_GRAD_CAREER_PROGRAM_CODE_DATA"})})
public class CodeController {

    private static Logger logger = LoggerFactory.getLogger(CodeController.class);

    private static final String REPORT_TYPE_CODE="Report Type Code";
    private static final String CERTIFICATE_TYPE_CODE="Certificate Type Code";

    @Autowired
    CodeService codeService;

    @Autowired
    GradValidation validation;

    @Autowired
    ResponseHelper response;

    @GetMapping(EducGradReportApiConstants.GET_ALL_CERTIFICATE_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_CERTIFICATE)
    @Operation(summary = "Find all Certificate Types", description = "Get all Certificate Types", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradCertificateTypes>> getAllCertificateTypeCodeList() {
        logger.debug("getAllCertificateTypeCodeList : ");
        return response.GET(codeService.getAllCertificateTypeCodeList());
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_CERTIFICATE)
    @Operation(summary = "Find a Certificate Type by code", description = "Get a Certificate Type by Code", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradCertificateTypes> getSpecificCertificateTypeCode(@PathVariable String certTypeCode) {
        logger.debug("getSpecificCertificateTypeCode : ");
        GradCertificateTypes gradResponse = codeService.getSpecificCertificateTypeCode(certTypeCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NOT_FOUND();
        }
    }

    @PostMapping(EducGradReportApiConstants.GET_ALL_CERTIFICATE_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.CREATE_CERTIFICATE_TYPE)
    @Operation(summary = "Create a Certificate Type", description = "Create a Certificate Type", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradCertificateTypes>> createGradCertificateTypes(
            @Valid @RequestBody GradCertificateTypes gradCertificateTypes) {
        logger.debug("createGradCertificateTypes : ");
        validation.requiredField(gradCertificateTypes.getCode(), CERTIFICATE_TYPE_CODE);
        validation.requiredField(gradCertificateTypes.getDescription(), "Certificate Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createGradCertificateTypes(gradCertificateTypes));
    }

    @PutMapping(EducGradReportApiConstants.GET_ALL_CERTIFICATE_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.UPDATE_CERTIFICATE_TYPE)
    @Operation(summary = "Update a Certificate Type", description = "Update a Certificate Type", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradCertificateTypes>> updateGradCertificateTypes(
            @Valid @RequestBody GradCertificateTypes gradCertificateTypes) {
        logger.info("updateGradCertificateTypes : ");
        validation.requiredField(gradCertificateTypes.getCode(), CERTIFICATE_TYPE_CODE);
        validation.requiredField(gradCertificateTypes.getDescription(), "Certificate Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateGradCertificateTypes(gradCertificateTypes));
    }

    @DeleteMapping(EducGradReportApiConstants.GET_ALL_CERTIFICATE_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.DELETE_CERTIFICATE_TYPE)
    @Operation(summary = "Delete a Certificate Type", description = "Delete a Certificate", tags = {"Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteGradCertificateTypes(@Valid @PathVariable String certTypeCode) {
        logger.debug("deleteGradCertificateTypes : ");
        validation.requiredField(certTypeCode, CERTIFICATE_TYPE_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.DELETE(codeService.deleteGradCertificateTypes(certTypeCode));
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_REPORT_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_REPORT)
    @Operation(summary = "Find all Report Types", description = "Get all Report Types", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<GradReportTypes>> getAllReportTypeCodeList() {
        logger.debug("getAllReportTypeCodeList : ");
        return response.GET(codeService.getAllReportTypeCodeList());
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_REPORT_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_REPORT)
    @Operation(summary = "Find a Report Type", description = "Get a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<GradReportTypes> getSpecificReportTypeCode(@PathVariable String reportTypeCode) {
        logger.debug("getSpecificReportTypeCode : ");
        GradReportTypes gradResponse = codeService.getSpecificReportTypeCode(reportTypeCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NOT_FOUND();
        }
    }

    @PostMapping(EducGradReportApiConstants.GET_ALL_REPORT_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.CREATE_REPORT_TYPE)
    @Operation(summary = "Create a Report Type", description = "Create a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradReportTypes>> createGradReportTypes(
            @Valid @RequestBody GradReportTypes gradReportTypes) {
        logger.debug("createGradReportTypes : ");
        validation.requiredField(gradReportTypes.getCode(), REPORT_TYPE_CODE);
        validation.requiredField(gradReportTypes.getDescription(), "Report Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.CREATED(codeService.createGradReportTypes(gradReportTypes));
    }

    @PutMapping(EducGradReportApiConstants.GET_ALL_REPORT_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.UPDATE_REPORT_TYPE)
    @Operation(summary = "Update a Report Type", description = "Update a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<ApiResponseModel<GradReportTypes>> updateGradReportTypes(
            @Valid @RequestBody GradReportTypes gradReportTypes) {
        logger.info("updateGradReportTypes : ");
        validation.requiredField(gradReportTypes.getCode(), REPORT_TYPE_CODE);
        validation.requiredField(gradReportTypes.getDescription(), "Report Type Description");
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.UPDATED(codeService.updateGradReportTypes(gradReportTypes));
    }

    @DeleteMapping(EducGradReportApiConstants.GET_ALL_REPORT_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.DELETE_REPORT_TYPE)
    @Operation(summary = "Delete a Report Type", description = "Delete a Report Type", tags = {"Report Type"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    public ResponseEntity<Void> deleteGradReportTypes(@Valid @PathVariable String reportTypeCode) {
        logger.debug("deleteGradReportTypes : ");
        validation.requiredField(reportTypeCode, REPORT_TYPE_CODE);
        if (validation.hasErrors()) {
            validation.stopOnErrors();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response.DELETE(codeService.deleteGradReportTypes(reportTypeCode));
    }
    
    @PostMapping(EducGradReportApiConstants.GET_ALL_PROGRAM_CERTIFICATES_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_CERTIFICATE)
    @Operation(summary = "Find all Program Certificates", description = "Get all Program Certificates", tags = {"Program Certificate"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ProgramCertificateTranscript>> getProgramCertificateList(@RequestBody ProgramCertificateReq programCertificateReq) {
        logger.debug("getProgramCertificateList : ");
        return response.GET(codeService.getProgramCertificateList(programCertificateReq));
    }

    @PostMapping(EducGradReportApiConstants.GET_PROGRAM_TRANSCRIPTS_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_REPORT)
    @Operation(summary = "Find Program Transcript", description = "Get Program Transcript", tags = {"Program Transcripts"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<ProgramCertificateTranscript> getProgramTranscript(@RequestBody ProgramCertificateReq programCertificateReq) {
        logger.debug("getProgramTranscriptList : ");
        return response.GET(codeService.getProgramTranscript(programCertificateReq));
    }


    @GetMapping(EducGradReportApiConstants.GET_ALL_TRANSCRIPT_TYPE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_TRANSCRIPT)
    @Operation(summary = "Find all Transcript Types", description = "Get all Transcript Types", tags = {"Transcript"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<TranscriptTypes>> getAllTranscriptTypeCodeList() {
        logger.debug("getAllTranscriptTypeCodeList : ");
        return response.GET(codeService.getAllTranscriptTypeCodeList());
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_TRANSCRIPT_TYPE_BY_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_TRANSCRIPT)
    @Operation(summary = "Find a Transcript Type by code", description = "Get a Transcript Type by Code", tags = {"Transcript"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<TranscriptTypes> getSpecificTranscriptTypeCode(@PathVariable String tranTypeCode) {
        logger.debug("getSpecificTranscriptTypeCode : ");
        TranscriptTypes gradResponse = codeService.getSpecificTranscriptTypeCode(tranTypeCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NOT_FOUND();
        }
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_PROGRAM_CERTIFICATES_TRANSCRIPTS_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_PROGRAM_CERTIFICATE_TRANSCRIPT)
    @Operation(summary = "Find all Program Certificate Transcript", description = "Get all Program Certificate Transcript", tags = {"Program"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<ProgramCertificateTranscript>> getAllProgramCertificateTranscriptList() {
        logger.debug("getAllProgramCertificateTranscriptList : ");
        return response.GET(codeService.getAllProgramCertificateTranscriptList());
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_DOCUMENT_STATUS_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_DOCUMENT_STATUS)
    @Operation(summary = "Find all Document Status Codes", description = "Get all Document Status Codes", tags = {"Document Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<List<DocumentStatusCode>> getAllDocumentStatusCodeList() {
        logger.debug("getAllDocumentStatusCodeList : ");
        return response.GET(codeService.getAllDocumentStatusCodeList());
    }

    @GetMapping(EducGradReportApiConstants.GET_ALL_DOCUMENT_STATUS_CODE_MAPPING)
    @PreAuthorize(PermissionsConstants.READ_GRAD_DOCUMENT_STATUS)
    @Operation(summary = "Find a Document Status Code", description = "Get a Document Status Code", tags = {"Document Status"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "204", description = "NO CONTENT")})
    public ResponseEntity<DocumentStatusCode> getSpecificDocumentStatusCode(@PathVariable String documentStatusCode) {
        logger.debug("getSpecificDocumentStatusCode : ");
        DocumentStatusCode gradResponse = codeService.getSpecificDocumentStatusCode(documentStatusCode);
        if (gradResponse != null) {
            return response.GET(gradResponse);
        } else {
            return response.NOT_FOUND();
        }
    }
}
