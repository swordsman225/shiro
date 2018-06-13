package com.huawei.hicloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
	
	/*@Autowired
	private JedisClient jedisClient;*/

	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public Map<String, Object> hello(String name) {
		logger.info("### GreetingController method: hello ...");
		HashMap<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("code", 200);
		resultMap.put("msg", "Hello world!" + name);
		
		return resultMap;
	}
	
}
