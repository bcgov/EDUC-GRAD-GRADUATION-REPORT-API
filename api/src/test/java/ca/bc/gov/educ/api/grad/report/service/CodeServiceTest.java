package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.exception.GradBusinessRuleException;
import ca.bc.gov.educ.api.grad.report.model.dto.*;
import ca.bc.gov.educ.api.grad.report.model.entity.*;
import ca.bc.gov.educ.api.grad.report.repository.*;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings({"rawtypes"})
public class CodeServiceTest {

	@Autowired
	private CodeService codeService;

	@MockBean
	private GradCertificateTypesRepository gradCertificateTypesRepository;

	@MockBean
	private TranscriptTypesRepository transcriptTypesRepository;

	@MockBean
	private GradReportTypesRepository gradReportTypesRepository;

	@MockBean DocumentStatusCodeRepository documentStatusCodeRepository;

	@MockBean
	private ProgramCertificateTranscriptRepository programCertificateTranscriptRepository;

	@MockBean
	WebClient webClient;

	@Autowired
	GradValidation validation;
	
	@Test
	public void testGetAllCertificateTypesCodeList() {
		List<GradCertificateTypesEntity> gradCertificateTypeList = new ArrayList<>();
		GradCertificateTypesEntity obj = new GradCertificateTypesEntity();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		gradCertificateTypeList.add(obj);
		obj = new GradCertificateTypesEntity();
		obj.setCode("F");
		obj.setDescription("French Dogwood");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		gradCertificateTypeList.add(obj);
		Mockito.when(gradCertificateTypesRepository.findAll()).thenReturn(gradCertificateTypeList);
		codeService.getAllCertificateTypeCodeList();
	}
	
	@Test
	public void testGetSpecificCertificateTypeCode() {
		String certCode = "E";
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("E");
		objEntity.setDescription("English Dogwood");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(certCode)).thenReturn(ent);
		codeService.getSpecificCertificateTypeCode(certCode);
	}
	
	@Test
	public void testGetSpecificCertificateTypeCodeReturnsNull() {
		String certCode = "E";
		Mockito.when(gradCertificateTypesRepository.findById(certCode)).thenReturn(Optional.empty());
		codeService.getSpecificCertificateTypeCode(certCode);
	}
	
	@Test
	public void testGetAllReportTypesCodeList() {
		List<GradReportTypesEntity> gradReportTypeList = new ArrayList<>();
		GradReportTypesEntity obj = new GradReportTypesEntity();
		obj.setCode("TRAN");
		obj.setDescription("Transcript");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		gradReportTypeList.add(obj);
		obj = new GradReportTypesEntity();
		obj.setCode("ACHV");
		obj.setDescription("Achievement");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		gradReportTypeList.add(obj);
		Mockito.when(gradReportTypesRepository.findAll()).thenReturn(gradReportTypeList);
		codeService.getAllReportTypeCodeList();
	}
	
	@Test
	public void testGetSpecificReportTypeCode() {
		String reportCode = "TRAN";
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("TRAN");
		obj.setDescription("Transcript");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("TRAN");
		objEntity.setDescription("Transcript");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(reportCode)).thenReturn(ent);
		codeService.getSpecificReportTypeCode(reportCode);
	}
	
	@Test
	public void testGetSpecificReportTypeCodeReturnsNull() {
		String reportCode = "TRAN";
		Mockito.when(gradReportTypesRepository.findById(reportCode)).thenReturn(Optional.empty());
		codeService.getSpecificReportTypeCode(reportCode);
	}
	
	@Test
	public void testCreateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradCertificateTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradCertificateTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradCertificateTypes_codeAlreadyExists() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradCertificateTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(gradCertificateTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateGradCertificateTypes(obj);		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradCertificateTypes_codeAlreadyExists() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradCertificateTypes(obj);
		
	}
	
	@Test
	public void testCreateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradReportTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradReportTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradReportTypes_codeAlreadyExists() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradReportTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(ent);
		Mockito.when(gradReportTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.updateGradReportTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testUpdateGradReportTypes_codeAlreadyExists() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradReportTypes(obj);
		
	}

	@Test
	public void testGetAllTranscriptTypesCodeList() {
		List<TranscriptTypesEntity> transcriptTypeList = new ArrayList<>();
		TranscriptTypesEntity obj = new TranscriptTypesEntity();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		transcriptTypeList.add(obj);
		obj = new TranscriptTypesEntity();
		obj.setCode("F");
		obj.setDescription("French Dogwood");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		transcriptTypeList.add(obj);
		Mockito.when(transcriptTypesRepository.findAll()).thenReturn(transcriptTypeList);
		List<TranscriptTypes> pcList = codeService.getAllTranscriptTypeCodeList();
		assertThat(pcList.isEmpty()).isFalse();
		assertThat(pcList.size()).isEqualTo(2);
	}

	@Test
	public void testGetSpecificTranscriptTypeCode() {
		String tranTypeCode = "E";
		TranscriptTypes obj = new TranscriptTypes();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		TranscriptTypesEntity objEntity = new TranscriptTypesEntity();
		objEntity.setCode("E");
		objEntity.setDescription("English Dogwood");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<TranscriptTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(transcriptTypesRepository.findById(tranTypeCode)).thenReturn(ent);
		TranscriptTypes docStaCode = codeService.getSpecificTranscriptTypeCode(tranTypeCode);
		assertThat(docStaCode).isNotNull();
		assertThat(docStaCode.getCode()).isEqualTo(obj.getCode());
	}

	@Test
	public void testGetSpecificTranscriptTypeCodeReturnsNull() {
		String tranTypeCode = "E";
		Mockito.when(transcriptTypesRepository.findById(tranTypeCode)).thenReturn(Optional.empty());
		TranscriptTypes ttypes =  codeService.getSpecificTranscriptTypeCode(tranTypeCode);
		assertThat(ttypes).isNull();
	}

	@Test
	public void testGetAllProgramCertificateTranscriptList() {
		List<ProgramCertificateTranscriptEntity> pList = new ArrayList<>();
		ProgramCertificateTranscriptEntity obj = new ProgramCertificateTranscriptEntity();
		obj.setCertificateTypeCode("E");
		obj.setTranscriptTypeCode("E");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		pList.add(obj);
		obj = new ProgramCertificateTranscriptEntity();
		obj.setCertificateTypeCode("F");
		obj.setTranscriptTypeCode("T");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		pList.add(obj);
		Mockito.when(programCertificateTranscriptRepository.findAll()).thenReturn(pList);
		List<ProgramCertificateTranscript> pcList = codeService.getAllProgramCertificateTranscriptList();
		assertThat(pcList.isEmpty()).isFalse();
		assertThat(pcList.size()).isEqualTo(2);
	}

	@Test
	public void testGetProgramCertificateTranscriptList() {
		ProgramCertificateReq req = new ProgramCertificateReq();
		req.setProgramCode("2018-EN");
		req.setOptionalProgram(null);
		req.setSchoolCategoryCode("02");

		GradCertificateTypesEntity tTypes = new GradCertificateTypesEntity();
		tTypes.setCode("E");
		tTypes.setPaperType("YED2");

		List<ProgramCertificateTranscriptEntity> pList = new ArrayList<>();
		ProgramCertificateTranscriptEntity obj = new ProgramCertificateTranscriptEntity();
		obj.setCertificateTypeCode("E");
		obj.setTranscriptTypeCode("E");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		pList.add(obj);
		obj = new ProgramCertificateTranscriptEntity();
		obj.setCertificateTypeCode("F");
		obj.setTranscriptTypeCode("T");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		pList.add(obj);
		Mockito.when(programCertificateTranscriptRepository.findCertificates(req.getProgramCode(),req.getSchoolCategoryCode(),req.getOptionalProgram())).thenReturn(pList);
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCertificateTypeCode())).thenReturn(Optional.of(tTypes));
		List<ProgramCertificateTranscript> pcList = codeService.getProgramCertificateList(req);
		assertThat(pcList.isEmpty()).isFalse();
		assertThat(pcList.size()).isEqualTo(2);
	}

	@Test
	public void testGetProgramTranscriptList() {
		ProgramCertificateReq req = new ProgramCertificateReq();
		req.setProgramCode("2018-EN");
		req.setOptionalProgram(null);
		req.setSchoolCategoryCode("02");

		TranscriptTypesEntity tTypes = new TranscriptTypesEntity();
		tTypes.setCode("E");
		tTypes.setPaperType("YED4");

		ProgramCertificateTranscriptEntity obj = new ProgramCertificateTranscriptEntity();
		obj.setCertificateTypeCode("E");
		obj.setTranscriptTypeCode("E");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));

		Mockito.when(programCertificateTranscriptRepository.findTranscript(req.getProgramCode(),req.getSchoolCategoryCode())).thenReturn(obj);
		Mockito.when(transcriptTypesRepository.findById(obj.getTranscriptTypeCode())).thenReturn(Optional.of(tTypes));
		ProgramCertificateTranscript pcObj = codeService.getProgramTranscript(req);
		assertNotNull(pcObj);
	}

	@Test
	public void testGetAllReportTypesDocumentStatusCodeList() {
		List<DocumentStatusCodeEntity> documentStatusCodeList = new ArrayList<>();
		DocumentStatusCodeEntity obj = new DocumentStatusCodeEntity();
		obj.setCode("TRAN");
		obj.setDescription("Transcript");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		documentStatusCodeList.add(obj);
		obj = new DocumentStatusCodeEntity();
		obj.setCode("ACHV");
		obj.setDescription("Achievement");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		documentStatusCodeList.add(obj);
		Mockito.when(documentStatusCodeRepository.findAll()).thenReturn(documentStatusCodeList);
		List<DocumentStatusCode> docList = codeService.getAllDocumentStatusCodeList();
		assertThat(docList.isEmpty()).isFalse();
		assertThat(docList.size()).isEqualTo(2);
	}

	@Test
	public void testGetSpecificDocumentStatusCode() {
		String documentStatusCode = "TRAN";
		DocumentStatusCode obj = new DocumentStatusCode();
		obj.setCode("TRAN");
		obj.setDescription("Transcript");
		obj.setCreateUser("GRADUATION");
		obj.setUpdateUser("GRADUATION");
		obj.setCreateDate(new Date(System.currentTimeMillis()));
		obj.setUpdateDate(new Date(System.currentTimeMillis()));
		obj.toString();
		DocumentStatusCodeEntity objEntity = new DocumentStatusCodeEntity();
		objEntity.setCode("TRAN");
		objEntity.setDescription("Transcript");
		objEntity.setCreateUser("GRADUATION");
		objEntity.setUpdateUser("GRADUATION");
		objEntity.setCreateDate(new Date(System.currentTimeMillis()));
		objEntity.setUpdateDate(new Date(System.currentTimeMillis()));
		Optional<DocumentStatusCodeEntity> ent = Optional.of(objEntity);
		Mockito.when(documentStatusCodeRepository.findById(documentStatusCode)).thenReturn(ent);
		DocumentStatusCode docStaCode = codeService.getSpecificDocumentStatusCode(documentStatusCode);
		assertThat(docStaCode).isNotNull();
		assertThat(docStaCode.getCode()).isEqualTo(obj.getCode());
	}

	@Test
	public void testGetSpecificDocumentStatusCodeReturnsNull() {
		String documentStatusCode = "TRAN";
		Mockito.when(documentStatusCodeRepository.findById(documentStatusCode)).thenReturn(Optional.empty());
		DocumentStatusCode docStaCode = codeService.getSpecificDocumentStatusCode(documentStatusCode);
		assertThat(docStaCode).isNull();
	}
}
