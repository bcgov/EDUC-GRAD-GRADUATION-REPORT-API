package ca.bc.gov.educ.api.grad.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EducGradReportApiApplication {

    public static void main(String[] args) {
        log.debug("########Starting API");
        SpringApplication.run(EducGradReportApiApplication.class, args);
        log.debug("########Started API");
    }

}