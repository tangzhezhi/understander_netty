package org.tang.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.tang.cfg.SpringConfig;

public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		LOG.debug("Starting application context");
		@SuppressWarnings("resource")
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class);
		ctx.registerShutdownHook();
	}

}
