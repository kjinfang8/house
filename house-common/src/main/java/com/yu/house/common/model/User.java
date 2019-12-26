package com.yu.house.common.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class User {
	private Long id;// 用户id
	private String name;// 用户名称
	private String phone;// 用户电话
	private String email;// 用户邮箱
	private String aboutme;//关于
	private String passwd;// 用户密码
	private String avatar;// 图像
	private Integer type;// 用户类型：普通用户1、经纪人2
	private Date createtime;// 创建时间
	private Integer enable;// 是否激活
	private Long agencyId;// 经纪人id
	private String confirmPasswd;// 确认密码
	private MultipartFile avatarFile;// 图像文件
	private String newPassword;// 新密码
	private String key;// 链接key
	private String agencyName;//代理处，经销处
	
	
	
	public Long getId() {
		return id;
	}
	public String getConfirmPasswd() {
		return confirmPasswd;
	}
	public void setConfirmPasswd(String confirmPasswd) {
		this.confirmPasswd = confirmPasswd;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAboutme() {
		return aboutme;
	}
	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public MultipartFile getAvatarFile() {
		return avatarFile;
	}
	public void setAvatarFile(MultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", aboutme=" + aboutme
				+ ", passwd=" + passwd + ", avatar=" + avatar + ", type=" + type + ", createtime=" + createtime
				+ ", enable=" + enable + ", agencyId=" + agencyId + ", avatarFile=" + avatarFile + ", newPassword="
				+ newPassword + ", key=" + key + ", agencyName=" + agencyName + "]";
	}
	
}
