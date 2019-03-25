package com.business.struts2.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.business.struts2.domain.User;
import com.business.struts2.exception.MySQLException;
import com.business.struts2.web.utils.JDBCUtils;

public class UserDAO {
	private QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

	/**
	 * 登陆查询
	 * 
	 * @param user
	 * @return
	 */
	public User findByLogonNameAndLogonPwd(User user) {
		String sql = "select * from s_user where logonName = ? and logonPwd = ?";
		User logonUser;
		try {
			logonUser = queryRunner.query(sql,
					new BeanHandler<User>(User.class), user.getLogonName(),
					user.getLogonPwd());
			return logonUser;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySQLException(e);
		}
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	public void save(User user) {
		String sql = "insert into s_user values(null,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] args = { user.getUserName(), user.getLogonName(),
				user.getLogonPwd(), user.getSex(), user.getBirthday(),
				user.getEducation(), user.getTelephone(), user.getInterest(),
				user.getPath(), user.getFilename(), user.getRemark() };
		try {
			queryRunner.update(sql, args);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySQLException(e);
		}
	}

	/**
	 * 条件查询
	 * 
	 * @param user
	 * @return
	 */
	public List<User> findByCondition(User user) {
		// 根据用户姓名、性别、学历、是否上传简历 组合查询
		String sql = "select * from s_user where 1=1 ";
		List<String> argList = new ArrayList<String>(); // 参数列表
		if (user.getUserName() != null
				&& user.getUserName().trim().length() > 0) {
			sql += "and userName like ? ";
			argList.add("%" + user.getUserName() + "%");
		}
		if (user.getSex() != null && user.getSex().trim().length() > 0) {
			sql += "and sex = ? ";
			argList.add(user.getSex());
		}
		if (user.getEducation() != null
				&& user.getEducation().trim().length() > 0) {
			sql += "and education = ? ";
			argList.add(user.getEducation());
		}
		if (user.getIsUpload() != null
				&& user.getIsUpload().trim().length() > 0) {
			if (user.getIsUpload().equals("1")) {
				// 上传简历
				sql += "and filename is not null";
			} else if (user.getIsUpload().equals("2")) {
				// 没有上传简历
				sql += "and filename is null";
			}
		}

		try {
			List<User> users = queryRunner.query(sql,
					new BeanListHandler<User>(User.class), argList.toArray());
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySQLException(e);
		}

	}

	/**
	 * 根据 id 删除
	 * 
	 * @param user
	 */
	public void delete(User user) {
		String sql = "delete from s_user where userID = ?";
		try {
			queryRunner.update(sql, user.getUserID());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySQLException(e);
		}
	}

	/**
	 * 根据id 查询
	 * 
	 * @param userID
	 * @return
	 */
	public User findById(int userID) {
		String sql = "select * from s_user where userID = ?";
		try {
			User user = queryRunner.query(sql,
					new BeanHandler<User>(User.class), userID);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MySQLException(e);
		}
	}

	/**
	 * 修改员工
	 * 
	 * @param user
	 * @param isEditUpload
	 */
	public void update(User user, boolean isEditUpload) {
		if (isEditUpload) {
			// 修改简历
			String sql = "update s_user set userName= ? ,logonName=? , logonPwd =? ,sex=? ,birthday=?, education=? ,telephone=?, interest=?, path=? ,filename=?, remark=? where userID = ?";
			Object[] args = { user.getUserName(), user.getLogonName(),
					user.getLogonPwd(), user.getSex(), user.getBirthday(),
					user.getEducation(), user.getTelephone(),
					user.getInterest(), user.getPath(), user.getFilename(),
					user.getRemark(), user.getUserID() };
			try {
				queryRunner.update(sql, args);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new MySQLException(e);
			}
		} else {
			// 没有修改简历
			String sql = "update s_user set userName= ? ,logonName=? , logonPwd =? ,sex=? ,birthday=?, education=? ,telephone=?, interest=?, remark=? where userID = ?";
			Object[] args = { user.getUserName(), user.getLogonName(),
					user.getLogonPwd(), user.getSex(), user.getBirthday(),
					user.getEducation(), user.getTelephone(),
					user.getInterest(), user.getRemark(), user.getUserID() };
			try {
				queryRunner.update(sql, args);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new MySQLException(e);
			}
		}
	}
}
