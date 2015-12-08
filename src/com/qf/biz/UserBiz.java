package com.qf.biz;

import com.qf.dao.UserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.User;

/**
 * 用户业务逻辑类
 * 
 * @author TeanWang
 *
 */
public class UserBiz {
	
	private UserDao userDao = new  UserDaoImpl();
	
	/**
	 * 用户登录
	 * @param name 用户名
	 * @param pwd 密码
	 * @return 是否登录成功
	 */
	public boolean login(String name, String pwd) {
		User user = userDao.getUserByName(name);
		if(user != null && user.getPassword().equals(pwd)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 用户注册
	 * @param name 用户名
	 * @param pwd 密码
	 * @return 是否注册成功
	 */
	public boolean register(String name, String pwd) {
		return userDao.addUser(new User(name, pwd));
	}

}
