package com.qf.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qf.dao.DBHelper;
import com.qf.dao.UserDao;
import com.qf.entity.User;

/**
 * 用户数据访问接口实现类
 * 
 * @author TeanWang
 *
 */
public class UserDaoImpl implements UserDao {
	
	private DBHelper db = new DBHelper();

	@Override
	/**
	 * 添加一个用户
	 * @param user 要添加的用户对象
	 * @return 添加是否成功
	 */
	public boolean addUser(User user) {
		int result = db.executeUpdate("INSERT INTO tb_user VALUES (?,?)", user.getName(), user.getPassword());
		return result > 0 ? true : false;
	}

	@Override
	/**
	 * 根据用户名获取用户
	 * @param name 要查的用户名
	 * @return 返回查询的结果，如果没有此用户则返回<Strong>null</strong>
	 */
	public User getUserByName(String name) {
		ResultSet rs = db.executeQuery("SELECT name,password FROM tb_user WHERE name=?", name);
		User user = null;
		try {
			if(rs.next()) {
				user = new User();
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return user;
	}


}
