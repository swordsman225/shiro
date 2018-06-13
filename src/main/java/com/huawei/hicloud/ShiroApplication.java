package com.huawei.hicloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShiroApplication {

	private static final Logger logger = LoggerFactory.getLogger(ShiroApplication.class);
	
	public static void main(String[] args) {
		logger.info("### ShiroApplication start ...");
		SpringApplication.run(ShiroApplication.class, args);
	}

}
