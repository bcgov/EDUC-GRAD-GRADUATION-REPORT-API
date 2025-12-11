package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.exception.DomainServiceException;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GradReportSignatureImage;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl.DistrictImpl;
import ca.bc.gov.educ.api.grad.report.model.entity.GradReportSignatureImageEntity;
import ca.bc.gov.educ.api.grad.report.repository.SignatureImageRepository;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.transformer.GradReportSignatureTransformer;
import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class GradReportSignatureService {

    private static final String CLASS_NAME = GradReportSignatureService.class.getName();
    SignatureImageRepository signatureImageRepository;
    GradReportSignatureTransformer gradReportSignatureTransformer;
    WebClient webClient;
    ReportApiConstants constants;
    RESTService restService;

    @Autowired
    public GradReportSignatureService(SignatureImageRepository signatureImageRepository,
                                      GradReportSignatureTransformer gradReportSignatureTransformer,
                                      @Qualifier("reportApiClient") WebClient webClient,
                                      ReportApiConstants constants,
                                      RESTService restService) {
        this.signatureImageRepository = signatureImageRepository;
        this.gradReportSignatureTransformer = gradReportSignatureTransformer;
        this.webClient = webClient;
        this.constants = constants;
        this.restService = restService;
    }

    @Transactional
    public GradReportSignatureImage getSignatureImageBySignatureId(String id) {
        Optional<GradReportSignatureImageEntity> entity = signatureImageRepository.findById(UUID.fromString(id));
        if(!entity.isPresent()) {
            try {
                entity = Optional.of(new GradReportSignatureImageEntity());
                byte[] imageBinary = loadBlankImage("reports/resources/images/signatures/BLANK.png");
                entity.get().setGradReportSignatureCode("BLANK.png");
                entity.get().setSignatureContent(imageBinary);
            } catch (Exception e) {
                log.error("Unable to load BLANK image from resources", e);
            }
        }
        GradReportSignatureImage signatureImage = gradReportSignatureTransformer.transformToDTO(entity);
        return  signatureImage;
    }

    @Transactional
    public List<GradReportSignatureImage> getSignatureImages() {
        List<GradReportSignatureImageEntity> entities = signatureImageRepository.findAll();
        List<GradReportSignatureImage> result = new ArrayList();
        for(GradReportSignatureImageEntity entity: entities) {
            GradReportSignatureImage signatureImage = gradReportSignatureTransformer.transformToDTO(entity);

            DistrictImpl dist = null;
            try {
                dist = getDistrictInfo(entity.getGradReportSignatureCode());
            } catch (Exception e) {
                log.error(String.format("Cannot retrieve District information for: %s", entity.getGradReportSignatureCode()));
            }
            if(dist != null)
                signatureImage.setDistrictName(dist.getDistrictName());
            result.add(signatureImage);
        }
        return result;
    }

    @Transactional
    public GradReportSignatureImage getSignatureImageByCode(String code) {
        GradReportSignatureImageEntity entity = signatureImageRepository.findBySignatureCode(code);
        if(entity ==  null) {
            try {
                entity = new GradReportSignatureImageEntity();
                byte[] imageBinary = loadBlankImage("reports/resources/images/signatures/BLANK.png");
                entity.setGradReportSignatureCode("BLANK.png");
                entity.setSignatureContent(imageBinary);
            } catch (Exception e) {
                log.error("Unable to load BLANK image from resources", e);
                throw new DomainServiceException("Unable to load default blank image");
            }
        }
        return gradReportSignatureTransformer.transformToDTO(entity);
    }

    @Transactional
    public GradReportSignatureImage getSignatureImageWithDistInfoByCode(String code) {
        GradReportSignatureImage signatureImage = getSignatureImageByCode(code);

        DistrictImpl dist = null;
        try {
            dist = getDistrictInfo(signatureImage.getGradReportSignatureCode());
        } catch (Exception e) {
            log.error(String.format("Cannot retrieve District information for: %s", signatureImage.getGradReportSignatureCode()));
        }
        if(dist != null)
            signatureImage.setDistrictName(dist.getDistrictName());
        return  signatureImage;
    }

    @Transactional
    public GradReportSignatureImage saveSignatureImage(GradReportSignatureImage signatureImage) {
        GradReportSignatureImageEntity toBeSaved = gradReportSignatureTransformer.transformToEntity(signatureImage);
        if(toBeSaved.getSignatureId() != null) {
            Optional<GradReportSignatureImageEntity> existingEnity = signatureImageRepository.findById(toBeSaved.getSignatureId());
            if(existingEnity.isPresent()) {
                GradReportSignatureImageEntity signEntity = existingEnity.get();
                signEntity.setSignatureContent(signatureImage.getSignatureContent());
                return gradReportSignatureTransformer.transformToDTO(signatureImageRepository.save(signEntity));
            }
        }
        return gradReportSignatureTransformer.transformToDTO(signatureImageRepository.save(toBeSaved));
    }

    private byte[] loadBlankImage(String path) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        byte[] imageBytes = inputStream.readAllBytes();
        inputStream.close();
        return imageBytes;
    }

    public DistrictImpl getDistrictInfo(String districtCode) {
        return restService.get(String.format(constants.getDistrictByDistrictNumberUrl(), districtCode), DistrictImpl.class, webClient);
    }
}
