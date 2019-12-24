package com.yu.house.biz.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.yu.house.biz.mapper.UserMapper;
import com.yu.house.common.model.User;

/**
 * 邮箱服务类
 * 
 * @author jinfangyu
 *
 */
@Service
@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}") // 邮件来源
	private String from;

	@Value("${domain.name}")
	private String domainName;

	@Autowired
	private UserMapper userMapper;

	// 设置缓存
	private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100)//
			.expireAfterAccess(15, TimeUnit.MINUTES)//
			.removalListener(new RemovalListener<String, String>() {
				// 过期删除email
				@Override
				public void onRemoval(RemovalNotification<String, String> notification) {
					String email = notification.getValue();
					User user = new User();
					user.setEmail(email);
					// 查询是否存在这个等待激活的Email
					List<User> targetUser = userMapper.selectUsersByQuery(user);
					if (!targetUser.isEmpty() && Objects.equal(targetUser.get(0)//
							.getEnable(), 0)) {
						userMapper.delete(email);
					}
				}
			}).build();

	/**
	 * 发送邮件方法 1、缓存key-Email关系 2、借助spring email发送邮件 3、借助异步框架进行异步存在：Async
	 * 
	 * @param email
	 */
	@Async
	public void registerNotify(String email) {
		String randomKey = RandomStringUtils.randomAlphabetic(10);// 设置时间
		// 缓存设置
		registerCache.put(randomKey, email);
		// 借助spring email发送邮件
		String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
		// 发送邮件
		sendMail("房产平台激活邮件", url, email);
	}

	/**
	 * 发送邮件方法
	 * 
	 * @param string
	 * @param url
	 * @param email
	 */
	@Async
	public void sendMail(String title, String url, String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);//
		message.setSubject(title);// 邮箱标题
		message.setTo(email);// 发送 到用户邮箱
		message.setText(url);// 邮件url
		mailSender.send(message);
	}

	/**
	 * 激活用户状态
	 * @param key
	 * @return
	 */
	public boolean enable(String key) {
		// 获取邮件
		String email = registerCache.getIfPresent(key);
		if (StringUtils.isBlank(email)) {
			return false;
		}
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setEnable(1);
		//更新用户信息
		userMapper.update(updateUser);
		registerCache.invalidate(key);
		return true;
	}

}
