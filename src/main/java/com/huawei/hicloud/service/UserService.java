package com.huawei.hicloud.service;

import java.util.List;

public interface UserService {

	/**
	 * Get roles by username
	 * @param useranme
	 * @return
	 */
	public List<String> getRoles(String useranme);
	
	/**
	 * Get permissions by role
	 * @param role
	 * @return
	 */
	public List<String> getPermissions(String role);
	
}
