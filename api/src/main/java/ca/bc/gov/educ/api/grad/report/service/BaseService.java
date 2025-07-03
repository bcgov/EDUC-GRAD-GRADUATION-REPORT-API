package ca.bc.gov.educ.api.grad.report.service;

import ca.bc.gov.educ.api.grad.report.model.dto.ReportGradStudentData;
import ca.bc.gov.educ.api.grad.report.model.dto.TokenResponse;
import ca.bc.gov.educ.api.grad.report.model.dto.TokenResponseCached;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.concurrent.*;

@Slf4j
public abstract class BaseService {

    @PersistenceContext
    EntityManager entityManager;

    protected EducGradReportApiConstants constants;
    protected RESTService restService;
    protected WebClient graduationServiceWebClient;

    private TokenResponseCached tokenResponseCached;

    @Autowired
    protected BaseService(EducGradReportApiConstants constants, RESTService restService,
                       @Qualifier("graduationReportApiClient") WebClient graduationServiceWebClient) {
        this.constants = constants;
        this.restService = restService;
        this.graduationServiceWebClient = graduationServiceWebClient;
    }

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

    protected void processReportGradStudentDataTasksAsync(List<Callable<Object>> tasks, List<ReportGradStudentData> result) {
        if(tasks.isEmpty()) return;
        List<Future<Object>> executionResult;
        ExecutorService executorService = Executors.newWorkStealingPool();
        try {
            executionResult = executorService.invokeAll(tasks);
            for (Future<?> f : executionResult) {
                Object o = f.get();
                if(o instanceof Pair<?, ?>) {
                    Pair<PageRequest, List<ReportGradStudentData>> taskResult = (Pair<PageRequest, List<ReportGradStudentData>>) o;
                    result.addAll(taskResult.getRight());
                    log.debug("Page {} processed successfully", taskResult.getLeft().getPageNumber());
                } else {
                    log.error("Error during the task execution: {}", f.get());
                }
            }
        } catch (InterruptedException | ExecutionException ex) {
            log.error("Multithreading error during the task execution: {}", ex.getLocalizedMessage());
            Thread.currentThread().interrupt();
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
        return this.graduationServiceWebClient.post().uri(constants.getTokenUrl())
                .headers(h -> h.addAll(httpHeaders))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .bodyToMono(TokenResponse.class).block();
    }

}
