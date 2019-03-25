package com.business.struts2.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import sun.misc.BASE64Encoder;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.business.struts2.domain.User;
import com.business.struts2.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 员工所有操作 （登陆、增删改查）
 * 
 * 
 * 
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {

	private User user = new User();
	public User getModel() {
		return user;
	}
	/**
	 * 员工登陆
	 * 
	 * @return
	 */
	@InputConfig(resultName = "loginINPUT")
	// 修改workflow拦截器跳转视图
	public String login() {
		// 登陆数据 已经在 user中，传递业务层，查询
		UserService userService = new UserService();
		User logonUser = userService.login(user);
		// 判断是否登陆成功
		if (logonUser == null) {
			// 登陆失败
			this.addActionError(this.getText("loginfail"));
			return "loginINPUT";
		} else {
			// 登陆成功
			ServletActionContext.getRequest().getSession()
					.setAttribute("user", logonUser);
			return "loginSUCCESS";
		}
	}

	/**
	 * 员工添加
	 * 
	 * @return
	 * @throws IOException
	 */
	@InputConfig(resultName = "addINPUT")
	public String add() throws IOException {
		// 上传简历
		if (upload != null) {
			String uuidName = UUID.randomUUID().toString();
			String path = "/WEB-INF/upload/" + uuidName;
			File destFile = new File(ServletActionContext.getServletContext()
					.getRealPath(path));
			FileUtils.copyFile(upload, destFile);

			// 接收员工数据
			// 在user 保存 uuid文件路径 和 真实文件名
			user.setPath(path);
			user.setFilename(uploadFileName);
		}

		UserService userService = new UserService();
		userService.add(user);

		return "addSUCCESS";
	}

	private File upload;
	private String uploadContentType;
	private String uploadFileName; // 真实文件名

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * 员工列表查询
	 * 
	 * @return
	 */
	public String list() {
		// 所有添加参数都被封装 model对象
		UserService userService = new UserService();

		// 获得查询结果，将结果传递给jsp (值栈)
		users = userService.list(user);

		return "listSUCCESS";
	}

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	/**
	 * 员工删除
	 * 
	 * @return
	 */
	public String delete() {
		UserService userService = new UserService();
		// 删除简历
		user = userService.findById(user.getUserID());
		if (user.getPath() != null) {// 判断有简历删除
			File file = new File(ServletActionContext.getServletContext()
					.getRealPath(user.getPath()));
			file.delete();
		}

		// 删除数据库数据
		userService.delete(user);

		return "deleteSUCCESS";
	}

	/**
	 * 查看员工详细信息
	 * 
	 * @return
	 */
	public String view() {
		// userID 自动封装 model对象
		UserService userService = new UserService();
		user = userService.findById(user.getUserID());

		return "viewSUCCESS";
	}

	/**
	 * 下载简历 (struts2 下载 一个流、 两个头信息)
	 * 
	 * @return
	 */
	public String download() {
		UserService userService = new UserService();
		user = userService.findById(user.getUserID());

		return "downloadSUCCESS";
	}

	// 返回文件流
	public InputStream getInputStream() throws IOException {
		if (user == null || user.getPath() == null) {
			return null;
		}
		File file = new File(ServletActionContext.getServletContext()
				.getRealPath(user.getPath()));
		return new FileInputStream(file);
	}

	// 返回简历 MIME类型
	public String getContentType() {
		if (user == null || user.getFilename() == null) {
			return null;
		}
		return ServletActionContext.getServletContext().getMimeType(
				user.getFilename());
	}

	// 返回编码后的文件名
	public String getDownloadFilename() throws IOException {
		if (user == null || user.getFilename() == null) {
			return null;
		}
		return encodeDownloadFilename(user.getFilename(), ServletActionContext
				.getRequest().getHeader("user-agent"));
	}

	/**
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * 
	 * @param filename
	 *            下载文件名
	 * @param agent
	 *            客户端浏览器
	 * @return 编码后的下载附件名
	 * @throws IOException
	 */
	public String encodeDownloadFilename(String filename, String agent)
			throws IOException {
		if (agent.contains("Firefox")) { // 火狐浏览器
			filename = "=?UTF-8?B?"
					+ new BASE64Encoder().encode(filename.getBytes("utf-8"))
					+ "?=";
		} else { // IE及其他浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

	/**
	 * 编辑员工，查询员工信息回显到form表单
	 * 
	 * @return
	 */
	public String editview() {
		// 根据id查询，将结果数据 保存 getModel引用中
		UserService userService = new UserService();
		user = userService.findById(user.getUserID());

		return "editviewSUCCESS";
	}

	/**
	 * 修改员工信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@InputConfig(resultName = "editINPUT")
	public String edit() throws IOException {
		UserService userService = new UserService();
		if (upload == null) {
			// 员工没有上传 新简历
			// 修改简历之外其他字段
			userService.edit(user, false);
		} else {
			// 员工修改了简历
			// 替换原有简历，数据表修改简历相关信息
			// 1、上传新简历
			String uuidName = UUID.randomUUID().toString();
			String path = "/WEB-INF/upload/" + uuidName;
			File destFile = new File(ServletActionContext.getServletContext()
					.getRealPath(path));
			FileUtils.copyFile(upload, destFile);

			// 接收员工数据
			// 在user 保存 uuid文件路径 和 真实文件名
			user.setPath(path);
			user.setFilename(uploadFileName);
			// 2、删除旧简历
			User oldUser = userService.findById(user.getUserID());
			File oldFile = new File(ServletActionContext.getServletContext()
					.getRealPath(oldUser.getPath()));
			oldFile.delete();

			// 3、修改员工信息
			userService.edit(user, true);
		}
		return "editSUCCESS";
	}

}
