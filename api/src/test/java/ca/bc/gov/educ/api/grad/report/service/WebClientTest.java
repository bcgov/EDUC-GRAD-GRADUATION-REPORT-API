package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.exception.GradBusinessRuleException;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.EducGradCodeApiConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings({"rawtypes","unchecked"})
public class WebClientTest {

    @MockBean
    WebClient webClient;

    @Autowired
    private EducGradCodeApiConstants constants;

    @Autowired
	private CodeService codeService;
    
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;
    @Mock
    private Mono<Boolean> monoResponse;
	
	@Autowired
	private GradUngradReasonsRepository gradUngradReasonsRepository;
	
	@Autowired
	private GradCertificateTypesRepository gradCertificateTypesRepository;
	
	@Autowired
	private GradReportTypesRepository gradReportTypesRepository;
	
	@Autowired
	private GradRequirementTypesRepository gradRequirementTypesRepository;
	
	@Autowired
	private StudentStatusRepository studentStatusRepository;

    @Before
    public void setUp() {
        openMocks(this);
        gradUngradReasonsRepository.save(createUngradReasons());
        gradCertificateTypesRepository.save(createCertificateTypes());
        gradReportTypesRepository.save(createReportTypes());
        gradRequirementTypesRepository.save(createRequirementTypes());
        studentStatusRepository.save(createStudentStatuses());
    }

    

	@After
    public void tearDown() {

    }
    
    

    @Test
    public void testdeleteUngradReason_with_APICallReturnsFalse() {
    	String reasonCode = "DC"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradStudentUngradReasonByUngradReasonCode(), reasonCode))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(false);        
        int success = codeService.deleteGradUngradReasons(reasonCode, "accessToken");
        assertEquals(1, success);
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testdeleteUngradReason_with_APICallReturnsTrue() {
    	String reasonCode = "DC"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradStudentUngradReasonByUngradReasonCode(), reasonCode))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(true);        
        int success = codeService.deleteGradUngradReasons(reasonCode, "accessToken");
        assertEquals(0,success);
        
    }
    
    @Test
    public void testdeleteCertificateType_with_APICallReturnsFalse() {
    	String certificateCode = "E"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradStudentCertificateByCertificateTypeCode(), certificateCode))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(false);        
        int success = codeService.deleteGradCertificateTypes(certificateCode, "accessToken");
        assertEquals(1,success);
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testdeleteCertificateType_with_APICallReturnsTrue() {
    	String certificateCode = "E"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradStudentCertificateByCertificateTypeCode(), certificateCode))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(true);        
        int success = codeService.deleteGradCertificateTypes(certificateCode, "accessToken");
        assertEquals(0,success);
        
    }
    
    @Test
    public void testdeleteReportType_with_APICallReturnsFalse() {
    	String reportType = "ACHV"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradReportTypeByReportTypeCode(), reportType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(false);        
        int success = codeService.deleteGradReportTypes(reportType, "accessToken");
        assertEquals(1,success);
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testdeleteReportType_with_APICallReturnsTrue() {
    	String reportType = "ACHV"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradReportTypeByReportTypeCode(), reportType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(true);        
        int success = codeService.deleteGradReportTypes(reportType, "accessToken");
        assertEquals(0,success);
        
    }
    
    @Test
    public void testdeleteRequirementType_with_APICallReturnsFalse() {
    	String requirementType = "M"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByRequirementTypeCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(false);        
        int success = codeService.deleteGradRequirementTypes(requirementType, "accessToken");
        assertEquals(1,success);
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testdeleteRequirementType_with_APICallReturnsTrue() {
    	String requirementType = "M"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradRequirementTypeByRequirementTypeCode(), requirementType))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(true);        
        int success = codeService.deleteGradRequirementTypes(requirementType, "accessToken");
        assertEquals(0,success);
        
    }
    
    @Test
    public void testdeleteStudentStatus_with_APICallReturnsFalse() {
    	String studentStatus = "A"; 
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradStudentStatusByStatusCode(), studentStatus))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(false);        
        int success = codeService.deleteStudentStatus(studentStatus, "accessToken");
        assertEquals(1,success);
        
    }
    
    @Test(expected = GradBusinessRuleException.class)
    public void testdeleteStudentStatus_with_APICallReturnsTrue() {
    	String studentStatus = "A";  
    	when(this.webClient.get()).thenReturn(this.requestHeadersUriMock);
    	 when(this.requestHeadersUriMock.uri(String.format(constants.getGradStudentStatusByStatusCode(), studentStatus))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.headers(any(Consumer.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(boolean.class)).thenReturn(monoResponse);
        when(this.monoResponse.block()).thenReturn(true);        
        int success = codeService.deleteStudentStatus(studentStatus, "accessToken");
        assertEquals(0,success);
        
    }
    
    public GradUngradReasonsEntity createUngradReasons() {
    	GradUngradReasonsEntity objEntity = new GradUngradReasonsEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		return objEntity;
    }
    
    private StudentStatusEntity createStudentStatuses() {
    	StudentStatusEntity objEntity = new StudentStatusEntity();
		objEntity.setCode("A");
		objEntity.setDescription("Active");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		return objEntity;
	}

	private GradRequirementTypesEntity createRequirementTypes() {
		GradRequirementTypesEntity objEntity = new GradRequirementTypesEntity();
		objEntity.setCode("M");
		objEntity.setDescription("Match");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		return objEntity;
	}

	private GradReportTypesEntity createReportTypes() {
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("ACHV");
		objEntity.setDescription("Achievement Report");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		return objEntity;
	}

	private GradCertificateTypesEntity createCertificateTypes() {
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("E");
		objEntity.setDescription("English Dogwood");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		return objEntity;
	}
	
}

