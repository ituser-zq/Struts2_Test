package com.business.struts2.service;

import java.util.List;

import com.business.struts2.dao.UserDAO;
import com.business.struts2.domain.User;

public class UserService {
	/**
	 * 登陆
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user) {
		UserDAO userDAO = new UserDAO();
		return userDAO.findByLogonNameAndLogonPwd(user);
	}

	/**
	 * 添加员工
	 * 
	 * @param user
	 */
	public void add(User user) {
		UserDAO userDAO = new UserDAO();
		userDAO.save(user);
	}

	/**
	 * 根据输入条件，进行员工列表查询
	 * 
	 * @param user
	 * @return
	 */
	public List<User> list(User user) {
		UserDAO userDAO = new UserDAO();
		return userDAO.findByCondition(user);
	}

	/**
	 * 删除员工
	 * 
	 * @param user
	 */
	public void delete(User user) {
		UserDAO userDAO = new UserDAO();
		// 删除数据表记录
		userDAO.delete(user);
	}

	/**
	 * 根据id 查询
	 * 
	 * @param userID
	 * @return
	 */
	public User findById(int userID) {
		UserDAO userDAO = new UserDAO();
		return userDAO.findById(userID);
	}

	/**
	 * 修改功能
	 * 
	 * @param user
	 *            员工信息
	 * @param isEditUpload
	 *            是否修改简历
	 */
	public void edit(User user, boolean isEditUpload) {
		UserDAO userDAO = new UserDAO();
		userDAO.update(user, isEditUpload);
	}
}
