package ca.bc.gov.educ.api.grad.report.cache;

import ca.bc.gov.educ.api.grad.report.service.v2.SchoolCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableScheduling
class CacheInitializerIntegrationTest {

  @MockBean
  private SchoolCacheService schoolCacheService;
  @MockBean
  ClientRegistrationRepository clientRegistrationRepository;
  @MockBean
  OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

  @Autowired
  private CacheInitializer cacheInitializer;

  @Test
  void testLoadCacheOnStartup() {
    // Verify that the refreshCache method is called during startup
    verify(schoolCacheService, Mockito.times(1)).refreshCache();
  }
}