package ca.bc.gov.educ.api.grad.report.config;

import ca.bc.gov.educ.api.grad.report.util.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:messages.properties")
public class GradReportConfig implements WebMvcConfigurer {

	@Autowired
	RequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor).addPathPatterns("/**");;
	}

	@Bean
	public MessageHelper messageHelper() {
		return new MessageHelper();
	}

	
}
