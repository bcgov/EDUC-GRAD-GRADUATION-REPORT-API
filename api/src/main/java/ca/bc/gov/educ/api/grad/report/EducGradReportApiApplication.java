package ca.bc.gov.educ.api.grad.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EducGradReportApiApplication {

    private static Logger logger = LoggerFactory.getLogger(EducGradReportApiApplication.class);

    public static void main(String[] args) {
        logger.debug("########Starting API");
        SpringApplication.run(EducGradReportApiApplication.class, args);
        logger.debug("########Started API");
    }

}