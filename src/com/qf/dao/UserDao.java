package com.qf.dao;

import com.qf.entity.User;
/**
 * 用户数据访问接口
 * 
 * @author TeanWang
 *
 */
public interface UserDao {
	/**
	 * 添加一个用户
	 * @param user 要添加的用户对象
	 * @return 添加是否成功
	 */
	boolean addUser(User user);

	/**
	 * 根据用户名获取用户
	 * @param name 要查的用户名
	 * @return 返回查询的结果，如果没有此用户则返回<Strong>null</strong>
	 */
	User getUserByName(String name);
}
