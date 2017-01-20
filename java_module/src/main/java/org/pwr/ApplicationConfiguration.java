package org.pwr;

import org.pwr.runners.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public TestBean getTb() {
		return new TestBean();
	}
	
	public String getAAA(){
		return env.getProperty("aaa");
	}

}
