package ca.bc.gov.educ.api.grad.report.config;

import ca.bc.gov.educ.api.grad.report.util.GradLocalDateDeserializer;
import ca.bc.gov.educ.api.grad.report.util.GradLocalDateSerializer;
import ca.bc.gov.educ.api.grad.report.util.GradLocalDateTimeDeserializer;
import ca.bc.gov.educ.api.grad.report.util.GradLocalDateTimeSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class GradReportConfig implements WebMvcConfigurer {

	RequestInterceptor requestInterceptor;

	@Autowired
	public GradReportConfig(RequestInterceptor requestInterceptor) {
		this.requestInterceptor = requestInterceptor;
	}

	@Bean
	public ModelMapper modelMapper() {

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
	}

	@Bean
	@Primary
	ObjectMapper jacksonObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(LocalDate.class, new GradLocalDateSerializer());
		simpleModule.addSerializer(LocalDateTime.class, new GradLocalDateTimeSerializer());
		simpleModule.addDeserializer(LocalDate.class, new GradLocalDateDeserializer());
		simpleModule.addDeserializer(LocalDateTime.class, new GradLocalDateTimeDeserializer());
		mapper.findAndRegisterModules();
		mapper.registerModule(simpleModule);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return mapper;
	}

}
