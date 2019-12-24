package com.yu.house.biz.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.yu.house.biz.mapper.UserMapper;
import com.yu.house.common.model.User;
import com.yu.house.common.utils.BeanHelper;
import com.yu.house.common.utils.HashUtils;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FileService fileService;

	@Value("${domain.name}")
	private String domainName;

	@Autowired
	private EmailService emailService;

	public List<User> getUser() {
		return userMapper.selectUser();
	}

	/**
	 * 加入数据库，非激活，密码加盐MD5,保存头像到本地 2、生成key，判定email 3、发送邮件
	 * 
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addAcount(User account) {
		account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
		// 获取用户上传文件图片
		System.err.println(" <-getAvatarFile()->" + account.getAvatarFile());// 获取文件路劲(对象)
		List<String> imgeList = fileService.getImgePaths(//
				Lists.newArrayList(account.getAvatarFile()));
		System.err.println(" <----imgeList----------> " + imgeList.get(0));
		
		if (!imgeList.isEmpty()&&imgeList!=null) {// 不为空
			account.setAvatar(imgeList.get(0));// 获取第一个
		}
		// 使用bean
		BeanHelper.setDefaultProp(account, User.class);
		BeanHelper.onInsert(account);
		account.setEnable(0);// 激活状态
		userMapper.insert(account);// 添加到数据库
		emailService.registerNotify(account.getEmail());// 发送邮件
		return true;
	}

}
