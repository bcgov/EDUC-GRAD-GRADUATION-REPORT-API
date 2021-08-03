package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.exception.GradBusinessRuleException;
import ca.bc.gov.educ.api.grad.report.model.dto.GradCertificateTypes;
import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.GradCertificateTypesEntity;
import ca.bc.gov.educ.api.grad.report.model.entity.GradReportTypesEntity;
import ca.bc.gov.educ.api.grad.report.repository.GradCertificateTypesRepository;
import ca.bc.gov.educ.api.grad.report.repository.GradReportTypesRepository;
import ca.bc.gov.educ.api.grad.report.util.GradValidation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
	private GradReportTypesRepository gradReportTypesRepository;

	@Autowired
	GradValidation validation;
	
	@Test
	public void testGetAllCertificateTypesCodeList() {
		List<GradCertificateTypesEntity> gradCertificateTypeList = new ArrayList<>();
		GradCertificateTypesEntity obj = new GradCertificateTypesEntity();
		obj.setCode("E");
		obj.setDescription("English Dogwood");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradCertificateTypeList.add(obj);
		obj = new GradCertificateTypesEntity();
		obj.setCode("F");
		obj.setDescription("French Dogwood");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
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
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("E");
		objEntity.setDescription("English Dogwood");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
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
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		gradReportTypeList.add(obj);
		obj = new GradReportTypesEntity();
		obj.setCode("ACHV");
		obj.setDescription("Achievement");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
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
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("TRAN");
		objEntity.setDescription("Transcript");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
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
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradCertificateTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradCertificateTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradCertificateTypes_codeAlreadyExists() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradCertificateTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradCertificateTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradCertificateTypes() {
		GradCertificateTypes obj = new GradCertificateTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
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
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		obj.toString();
		GradCertificateTypesEntity objEntity = new GradCertificateTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradCertificateTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradCertificateTypes(obj);
		
	}
	
	@Test
	public void testCreateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		Mockito.when(gradReportTypesRepository.save(objEntity)).thenReturn(objEntity);
		codeService.createGradReportTypes(obj);
		
	}
	
	@Test(expected = GradBusinessRuleException.class)
	public void testCreateGradReportTypes_codeAlreadyExists() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by School");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Optional<GradReportTypesEntity> ent = Optional.of(objEntity);
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(ent);
		codeService.createGradReportTypes(obj);
		
	}
	
	@Test
	public void testUpdateGradReportTypes() {
		GradReportTypes obj = new GradReportTypes();
		obj.setCode("DC");
		obj.setDescription("Data Correction by Schools");
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
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
		obj.setCreatedBy("GRADUATION");
		obj.setUpdatedBy("GRADUATION");
		obj.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		obj.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		GradReportTypesEntity objEntity = new GradReportTypesEntity();
		objEntity.setCode("DC");
		objEntity.setDescription("Data Correction by School");
		objEntity.setCreatedBy("GRADUATION");
		objEntity.setUpdatedBy("GRADUATION");
		objEntity.setCreatedTimestamp(new Date(System.currentTimeMillis()));
		objEntity.setUpdatedTimestamp(new Date(System.currentTimeMillis()));
		Mockito.when(gradReportTypesRepository.findById(obj.getCode())).thenReturn(Optional.empty());
		codeService.updateGradReportTypes(obj);
		
	}
}
