package com.qf.dao.impl;

import com.qf.dao.UserDao;
import com.qf.entity.User;

/**
 * 用户数据访问接口实现类
 * 
 * @author TeanWang
 *
 */
public class UserDaoImpl implements UserDao {

	@Override
	/**
	 * 添加一个用户
	 * @param user 要添加的用户对象
	 * @return 添加是否成功
	 */
	public boolean addUser(User user) {
		return false;
	}

	@Override
	/**
	 * 根据用户名获取用户
	 * @param name 要查的用户名
	 * @return 返回查询的结果，如果没有此用户则返回<Strong>null</strong>
	 */
	public User getUserByName(String name) {
		return null;
	}

}
