package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.cache.CacheInitializer;
import ca.bc.gov.educ.api.grad.report.exception.ServiceException;
import ca.bc.gov.educ.api.grad.report.util.ThreadLocalStateUtil;
import io.netty.channel.ConnectTimeoutException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class RESTServicePOSTTest {

    @Autowired
    private RESTService restService;

    @MockBean
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @MockBean
    private WebClient.RequestBodySpec requestBodyMock;
    @MockBean
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @MockBean
    private WebClient.ResponseSpec responseMock;
    @MockBean(name = "webClient")
    WebClient webClient;

    @MockBean
    @Qualifier("graduationReportClient")
    WebClient getGraduationReportClient;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    @MockBean
    OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
    @MockBean
    private CacheInitializer cacheInitializer;

    private static final byte[] TEST_BYTES = "The rain in Spain stays mainly on the plain.".getBytes();
    private static final String TEST_BODY = "{test:test}";
    private static final String TEST_URL = "https://fake.url.com";

    @Before
    public void setUp(){
        Mockito.reset(webClient, getGraduationReportClient, responseMock, requestHeadersMock, requestBodyMock, requestBodyUriMock);
        ThreadLocalStateUtil.clear();
        when(this.webClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.getGraduationReportClient.post()).thenReturn(this.requestBodyUriMock);
        when(this.requestBodyUriMock.uri(any(String.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.headers(any(Consumer.class))).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.contentType(any())).thenReturn(this.requestBodyMock);
        when(this.requestBodyMock.body(any(BodyInserter.class))).thenReturn(this.requestHeadersMock);
        when(this.requestHeadersMock.retrieve()).thenReturn(this.responseMock);
        when(this.responseMock.bodyToMono(byte[].class)).thenReturn(Mono.just(TEST_BYTES));
    }

    @Test
    public void testPostOverride_GivenProperData_Expect200Response(){
        ThreadLocalStateUtil.setCorrelationID("test-correlation-id");
        ThreadLocalStateUtil.setCurrentUser("test-user");
        when(this.responseMock.onStatus(any(), any())).thenReturn(this.responseMock);
        byte[] response = this.restService.post(TEST_URL, TEST_BODY, byte[].class);
        Assert.assertArrayEquals(TEST_BYTES, response);
    }

    @Test(expected = ServiceException.class)
    public void testPostOverride_Given4xxErrorFromService_ExpectServiceError() {
        when(this.responseMock.onStatus(any(), any())).thenThrow(new ServiceException("Error", 500));
        this.restService.post(TEST_URL, TEST_BODY, byte[].class);
    }

    @Test
    public void testPostForList_GivenProperData_Expect200Response() {
        ThreadLocalStateUtil.setCorrelationID("test-correlation-id");
        ThreadLocalStateUtil.setCurrentUser("test-user");
        List<String> expectedResponse = List.of("response1", "response2");
        when(this.responseMock.bodyToFlux(String.class)).thenReturn(Flux.fromIterable(expectedResponse));
        when(this.responseMock.onStatus(any(), any())).thenReturn(this.responseMock);

        List<String> response = this.restService.postForList(TEST_URL, TEST_BODY, String.class);
        Assert.assertEquals(expectedResponse, response);
    }

    @Test(expected = ServiceException.class)
    public void testPostForList_Given5xxErrorFromService_ExpectServiceError() {
        ThreadLocalStateUtil.setCorrelationID("test-correlation-id");
        ThreadLocalStateUtil.setCurrentUser("test-user");
        when(this.responseMock.onStatus(any(), any())).thenThrow(new ServiceException("Error", 500));
        this.restService.postForList(TEST_URL, TEST_BODY, String.class);
    }

    @Test(expected = ServiceException.class)
    public void testPost_Given5xxErrorFromService_ExpectConnectionError(){
        ThreadLocalStateUtil.setCorrelationID("test-correlation-id");
        ThreadLocalStateUtil.setCurrentUser("test-user");
        when(requestBodyUriMock.uri(TEST_URL)).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);

        when(responseMock.bodyToMono(byte[].class)).thenReturn(Mono.error(new ConnectTimeoutException("Connection closed")));
        this.restService.post(TEST_URL, TEST_BODY, byte[].class);
    }

    @Test(expected = ServiceException.class)
    public void testPost_Given5xxErrorFromService_ExpectWebClientRequestError(){
        ThreadLocalStateUtil.setCorrelationID("test-correlation-id");
        ThreadLocalStateUtil.setCurrentUser("test-user");
        when(requestBodyUriMock.uri(TEST_URL)).thenReturn(requestBodyMock);
        when(requestBodyMock.retrieve()).thenReturn(responseMock);

        Throwable cause = new RuntimeException("Simulated cause");
        when(responseMock.bodyToMono(byte[].class)).thenReturn(Mono.error(new WebClientRequestException(cause, HttpMethod.POST, null, new HttpHeaders())));
        this.restService.post(TEST_URL, TEST_BODY, byte[].class);
    }

    @Test
    public void testPostForList_Given5xxErrorFromService_ExpectConnectionError() {
        ThreadLocalStateUtil.setCorrelationID("test-correlation-id");
        ThreadLocalStateUtil.setCurrentUser("test-user");
        List<String> expectedResponse = List.of("response1", "response2");
        when(this.responseMock.bodyToFlux(String.class)).thenReturn(Flux.fromIterable(expectedResponse));
        when(this.responseMock.onStatus(any(), any())).thenReturn(this.responseMock);

        when(responseMock.bodyToMono(byte[].class)).thenReturn(Mono.error(new ConnectTimeoutException("Connection closed")));
        this.restService.postForList(TEST_URL, TEST_BODY, String.class);
    }

    @Test
    public void testPostForList_Given5xxErrorFromService_ExpectWebClientRequestError() {
        List<String> expectedResponse = List.of("response1", "response2");
        when(this.responseMock.bodyToFlux(String.class)).thenReturn(Flux.fromIterable(expectedResponse));
        when(this.responseMock.onStatus(any(), any())).thenReturn(this.responseMock);

        Throwable cause = new RuntimeException("Simulated cause");
        when(responseMock.bodyToMono(byte[].class)).thenReturn(Mono.error(new WebClientRequestException(cause, HttpMethod.POST, null, new HttpHeaders())));
        this.restService.postForList(TEST_URL, TEST_BODY, String.class);
    }
}
