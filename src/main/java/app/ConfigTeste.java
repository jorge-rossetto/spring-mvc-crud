package app;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import app.view.DateFormatter;

@Configuration
//@EnableAutoConfiguration
public class ConfigTeste  extends WebMvcConfigurerAdapter {
	
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter());
	}
	
}
