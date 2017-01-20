package org.pwr.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

//@Configuration
//// @PropertySource("classpath:/resources/app.properties")
//public class AppConfig {
//
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
//		props.setLocations(new Resource[] { new ClassPathResource("/resources/app.properties")
//
//		});
//		return props;
//	}
//}
