package com.huawei.hicloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.huawei.hicloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	/**
	 * Get roles by username.
	 */
	@Override
	public List<String> getRoles(String useranme) {
		ArrayList<String> roles = new ArrayList<>();
		
		roles.add("admin");
		
		return roles;
	}

	/**
	 * Get permissions by role name.
	 */
	@Override
	public List<String> getPermissions(String role) {
		ArrayList<String> permissions = new ArrayList<>();
		
		permissions.add("rs:create");
		permissions.add("rs:view");
		permissions.add("rs:update");
		permissions.add("rs:del");
		
		return permissions;
	}

	
	
}
