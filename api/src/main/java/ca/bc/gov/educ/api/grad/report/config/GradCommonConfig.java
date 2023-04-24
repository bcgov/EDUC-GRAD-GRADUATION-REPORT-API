package ca.bc.gov.educ.api.grad.report.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@Configuration
@PropertySource("classpath:messages.properties")
public class GradCommonConfig {

	@Bean
	ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		return builder.createXmlMapper(false)
				// Set timezone for JSON serialization as system timezone
				.timeZone(TimeZone.getDefault())
				.build();
	}

	@Bean
	Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder();
	}
}
