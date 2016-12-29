package com.niit.collaboration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


      //similar to dispatcher-servlet.xml

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.niit.collaboration")

public class AppConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public InternalResourceViewResolver viewResolver(){
	
		
		logger.debug("Starting of the Method ViewResolver");
		
		InternalResourceViewResolver viewresolver = new InternalResourceViewResolver(); 
		viewresolver.setViewClass(JstlView.class);
		viewresolver.setPrefix("/WEB-INF/views/");
		viewresolver.setSuffix(".jsp");
		
		return viewresolver; 
		
		
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	}
	
	
	
	

}
