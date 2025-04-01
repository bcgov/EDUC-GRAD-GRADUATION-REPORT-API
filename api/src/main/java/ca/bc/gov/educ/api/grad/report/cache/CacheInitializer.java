package ca.bc.gov.educ.api.grad.report.cache;

import ca.bc.gov.educ.api.grad.report.service.v2.SchoolCacheService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("!test")
@Slf4j
@Component
public class CacheInitializer {

    private final SchoolCacheService schoolCacheService;

    public CacheInitializer(SchoolCacheService schoolCacheService) {
        this.schoolCacheService = schoolCacheService;
    }

    @PostConstruct
    public void loadCacheOnStartup() {
        log.info("Initializing cache at startup...");
        schoolCacheService.refreshCache();
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void scheduledCacheRefresh() {
        log.info("Refreshing cache...");
        schoolCacheService.refreshCache();
    }
}