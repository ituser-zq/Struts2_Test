package com.business.struts2.domain;

public class User {
	private int userID;
	private String userName;
	private String logonName;
	private String logonPwd;
	private String sex;
	private String birthday;
	private String education;
	private String telephone;
	private String interest;
	private String path; // 存放UUID文件名 和路径
	private String filename; // 存放真实文件名
	private String remark;

	// 是否上传简历
	private String isUpload;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getLogonPwd() {
		return logonPwd;
	}

	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName
				+ ", logonName=" + logonName + ", logonPwd=" + logonPwd
				+ ", sex=" + sex + ", birthday=" + birthday + ", education="
				+ education + ", telephone=" + telephone + ", interest="
				+ interest + ", path=" + path + ", filename=" + filename
				+ ", remark=" + remark + "]";
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

}

// CREATE TABLE S_User(
// userID INT NOT NULL AUTO_INCREMENT, #主键ID
// userName VARCHAR(50) NULL, #用户姓名
// logonName VARCHAR(50) NULL, #登录名
// logonPwd VARCHAR(50) NULL, #密码#
// sex VARCHAR(10) NULL, #性别（例如：男，女）
// birthday VARCHAR(50) NULL, #出生日期
// education VARCHAR(20) NULL, #学历（例如：研究生、本科、专科、高中）
// telephone VARCHAR(50) NULL, #电话
// interest VARCHAR(20) NULL, #兴趣爱好（例如：体育、旅游、逛街）
// path VARCHAR(500) NULL, #上传路径（path路径）
// filename VARCHAR(100) NULL, #上传文件名称（文件名）
// remark VARCHAR(500) NULL, #备注
// PRIMARY KEY (userID)
// );