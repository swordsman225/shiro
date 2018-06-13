package com.huawei.hicloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiroController {

	@RequiresRoles("admin")
	@RequiresPermissions("rs:view")
	@RequestMapping(value="/shiro", method=RequestMethod.GET)
	public Map<String, Object> shiro() {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("msg", "Hello shiro!");
		
		return resultMap;
	}
	
}
