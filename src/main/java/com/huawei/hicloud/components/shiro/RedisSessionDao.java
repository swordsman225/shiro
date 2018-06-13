package com.huawei.hicloud.components.shiro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.huawei.hicloud.components.jedis.JedisClient;

public class RedisSessionDao extends EnterpriseCacheSessionDAO {

	private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
	
	/** Session过期时间,单位: 秒 */
	private static final Integer SESSION_TIMEOUT = 30*60;

	@Autowired
	private JedisClient jedisClient;

	// 创建session，保存到数据库
	@Override
	protected Serializable doCreate(Session session) {
		logger.info("### doCreate session: " + JSON.toJSONString(session));
		Serializable sessionId = super.doCreate(session);
		byte[] key = sessionId.toString().getBytes();
		jedisClient.set(key, sessionToByte(session));
		jedisClient.expire(key, SESSION_TIMEOUT);

		return sessionId;
	}

	// 获取session
	@Override
	protected Session doReadSession(Serializable sessionId) {
		logger.info("### doReadSession sessionId: {}", sessionId);

		// 先从缓存中获取session，如果没有再去数据库中获取
		Session session = super.doReadSession(sessionId);
		if (session == null) {
			byte[] key = sessionId.toString().getBytes();
			byte[] bytes = jedisClient.get(key);
			if (bytes != null && bytes.length > 0) {
				session = byteToSession(bytes);
			}
			jedisClient.expire(key, SESSION_TIMEOUT);
		}
		
		return session;
	}

	// 更新session的最后一次访问时间
	@Override
	protected void doUpdate(Session session) {
		logger.info("### doUpdate session: " + JSON.toJSONString(session));
		super.doUpdate(session);
		byte[] key = session.getId().toString().getBytes();
		jedisClient.set(key, sessionToByte(session));
		jedisClient.expire(key, SESSION_TIMEOUT);
	}

	// 删除session
	@Override
	protected void doDelete(Session session) {
		logger.info("### doDelete session: " + JSON.toJSONString(session));
		super.doDelete(session);
		jedisClient.del(session.getId().toString().getBytes());
	}

	// 把session对象转化为byte保存到redis中
	public byte[] sessionToByte(Session session) {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(session);
			bytes = bo.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	// 把byte还原为session
	public Session byteToSession(byte[] bytes) {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream in;
		SimpleSession session = null;
		try {
			in = new ObjectInputStream(bi);
			session = (SimpleSession) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return session;
	}

}