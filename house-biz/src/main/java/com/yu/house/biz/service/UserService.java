package com.yu.house.biz.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@Value("${file.prefix}")
	private String imgPrefix;

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
		List<String> imgeList = fileService.getImgePaths(//
				Lists.newArrayList(account.getAvatarFile()));
		System.err.println(" <----imgeList----------> " + imgeList.get(0));

		if (!imgeList.isEmpty()) {// 不为空
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

	/**
	 * 更改注册用户状态
	 * 
	 * @param key
	 * @return
	 */
	public boolean enable(String key) {
		return emailService.enable(key);
	}

	/**
	 * 用户登录验证
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User auth(String username, String password) {
		User user = new User();
		user.setEmail(username);
		user.setPasswd(HashUtils.encryPassword(password));
		user.setEnable(1);
		List<User> list = getUserByQuery(user);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * sql查询
	 * 
	 * @param user
	 * @return
	 */
	private List<User> getUserByQuery(User user) {
		List<User> list = userMapper.selectUsersByQuery(user);
		list.forEach(u -> {
			u.setAvatar(imgPrefix + u.getAvatar());
		});
		return list;
	}
	
}
