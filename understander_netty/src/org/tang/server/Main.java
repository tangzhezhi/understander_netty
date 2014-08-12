package org.tang.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tang.cfg.SpringConfig;
import org.tang.cfg.SpringDaoConfig;
import org.tang.cfg.SpringServiceConfig;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	
	  public static ConfigurableApplicationContext getApplicationContext() {
		    return new ClassPathXmlApplicationContext("applicationContext-*");
	  }
	
	public static void main(String[] args) {
		LOG.debug("Starting application context");
		
		getApplicationContext();
		
		@SuppressWarnings("resource")
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class,SpringDaoConfig.class,SpringServiceConfig.class);

		ctx.registerShutdownHook();
	}

}
