package com.huawei.hicloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestBody Map<String, String> params) {
		HashMap<String, Object> result = new HashMap<>();

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("shiro", "123456");

		try {
			subject.login(token);
			result.put("token", subject.getSession().getId() + "");
			result.put("msg", "登录成功");
		} catch (IncorrectCredentialsException e) {
			result.put("msg", "密码错误");
		} catch (LockedAccountException e) {
			result.put("msg", "登录失败，该用户已被冻结");
		} catch (AuthenticationException e) {
			result.put("msg", "该用户不存在");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
