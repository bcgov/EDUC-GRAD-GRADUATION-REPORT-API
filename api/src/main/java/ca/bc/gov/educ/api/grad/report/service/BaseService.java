package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.model.dto.ReportGradStudentData;
import ca.bc.gov.educ.api.grad.report.model.dto.TokenResponse;
import ca.bc.gov.educ.api.grad.report.model.dto.TokenResponseCached;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class BaseService {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    EducGradReportApiConstants constants;

    @Autowired
    WebClient webClient;

    private TokenResponseCached tokenResponseCached;

    protected boolean isClobDataChanged(String currentBase64, String newBase64) {
        if (currentBase64 == null || newBase64 == null) {
            return true;
        }
        if (currentBase64.length() != newBase64.length()) {
            return true;
        }
        return !currentBase64.equals(newBase64);
    }

    @SneakyThrows
    protected UUID convertStringToGuid(String studentGuid) {
        byte[] data = Hex.decodeHex(studentGuid.toCharArray());
        return new UUID(ByteBuffer.wrap(data, 0, 8).getLong(), ByteBuffer.wrap(data, 8, 8).getLong());
    }

    protected void processReportGradStudentDataTasksAsync(List<Callable<Object>> tasks, List<ReportGradStudentData> result, int numberOfThreads) {
        List<Future<Object>> executionResult;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        try {
            executionResult = executorService.invokeAll(tasks);
            for (Future<?> f : executionResult) {
                Object o = f.get();
                if(o instanceof Pair<?, ?>) {
                    Pair<PageRequest, List<ReportGradStudentData>> taskResult = (Pair<PageRequest, List<ReportGradStudentData>>) o;
                    result.addAll(taskResult.getRight());
                    logger.debug("Page {} processed successfully", taskResult.getLeft().getPageNumber());
                } else {
                    logger.error("Error during the task execution: {}", f.get().toString());
                }
            }
        } catch (Exception ex) {
            logger.error(ex.toString());
        } finally {
            executorService.shutdown();
        }
    }

    protected String fetchAccessToken() {
        if(tokenResponseCached == null || tokenResponseCached.isExpired()) {
            this.tokenResponseCached = new TokenResponseCached(10);
            this.tokenResponseCached.setTokenResponse(getTokenResponse());
        }
        return tokenResponseCached.getTokenResponse().getAccess_token();
    }

    private TokenResponse getTokenResponse() {
        HttpHeaders httpHeaders = EducGradReportApiConstants.getHeaders(
                constants.getUserName(), constants.getPassword());
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        return this.webClient.post().uri(constants.getTokenUrl())
                .headers(h -> h.addAll(httpHeaders))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .bodyToMono(TokenResponse.class).block();
    }

}
