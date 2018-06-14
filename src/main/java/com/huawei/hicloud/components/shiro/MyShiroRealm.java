package com.huawei.hicloud.components.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.huawei.hicloud.service.UserService;

/**
 * 认证/授权
 * 
 * @author
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Autowired
	private UserService userService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("##### doGetAuthorizationInfo args: " + principalCollection);
		
		// 获取用户名
		String username = (String) principalCollection.getPrimaryPrincipal();

		// 通过用户名获取用户权限信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		List<String> roles = userService.getRoles(username);
		simpleAuthorizationInfo.addRoles(roles);
		for (String role : roles) {
			List<String> permissions = userService.getPermissions(role);
			simpleAuthorizationInfo.addStringPermissions(permissions);
		}

		return simpleAuthorizationInfo;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		logger.info("##### doGetAuthenticationInfo args: " + authenticationToken);

		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 获取登录信息
		String userName = token.getUsername();
		char[] password = token.getPassword();
		
		// 查询数据库获取用户信息,校验密码,判断用户状态
		
		// 加入判断逻辑
		/*
		 * if (userInfo.getState() == 1) { // 账户冻结 throw new LockedAccountException(); }
		 */
		

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, password,
				ByteSource.Util.bytes(userName), getName());

		return authenticationInfo;
	}

}
