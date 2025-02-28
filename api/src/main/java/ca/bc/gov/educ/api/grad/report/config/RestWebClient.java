package ca.bc.gov.educ.api.grad.report.config;

import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import ca.bc.gov.educ.api.grad.report.util.LogHelper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
@Profile("!test")
public class RestWebClient {
    private final DefaultUriBuilderFactory factory;
    private final ClientHttpConnector connector;
    EducGradReportApiConstants constants;

    @Autowired
    public RestWebClient(EducGradReportApiConstants constants) {
        this.constants = constants;
        this.factory = new DefaultUriBuilderFactory();
        this.factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        final HttpClient client = HttpClient.create().compress(true);
        client.warmup()
            .block();
        this.connector = new ReactorClientHttpConnector(client);
    }

    @Bean("graduationReportClient")
    @Autowired
    WebClient getGraduationReportClient(final WebClient.Builder builder) {
        val clientRegistryRepo = new InMemoryReactiveClientRegistrationRepository(ClientRegistration
            .withRegistrationId(this.constants.getGradReportClientUserName())
            .tokenUri(this.constants.getGradReportClientTokenUrl())
            .clientId(this.constants.getGradReportClientUserName())
            .clientSecret(this.constants.getGradReportClientPassword())
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .build());
        val clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistryRepo);
        val authorizedClientManager =
            new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistryRepo, clientService);
        val oauthFilter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauthFilter.setDefaultClientRegistrationId(this.constants.getGradReportClientUserName());
        return builder
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(100 * 1024 * 1024))
            .filter(this.log())
            .clientConnector(this.connector)
            .uriBuilderFactory(this.factory)
            .filter(oauthFilter)
            .build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                    .defaultCodecs()
                    .maxInMemorySize(300 * 1024 * 1024))  // 300MB
                .build())
            .filter(this.log())
            .build();
    }

    private ExchangeFilterFunction log() {
        return (clientRequest, next) -> next
            .exchange(clientRequest)
            .doOnNext((clientResponse -> LogHelper.logClientHttpReqResponseDetails(
                clientRequest.method(),
                clientRequest.url().toString(),
                //GRAD2-1929 Refactoring/Linting replaced rawStatusCode() with statusCode() as it was deprecated.
                clientResponse.statusCode().value(),
                clientRequest.headers().get(EducGradReportApiConstants.CORRELATION_ID),
                constants.isSplunkLogHelperEnabled())
            ));
    }
}
