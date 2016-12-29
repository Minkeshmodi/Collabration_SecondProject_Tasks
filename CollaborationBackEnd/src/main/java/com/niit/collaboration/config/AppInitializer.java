package com.niit.collaboration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


//web.xml

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	
	private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);
	
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		logger.debug("starting of the Method getRootConfigclasses");
		return new Class[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		logger.debug("starting of the Method getServletConfigClasses");
		return new Class[] {AppConfig.class};
		
	}

	@Override
	protected String[] getServletMappings() {
		logger.debug("starting of the Method getServletMappings");
		
		return new String[] {"/"} ;
	}
	
	

	
	
}
