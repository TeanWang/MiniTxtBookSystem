package com.qf.dao;

import java.sql.ResultSet;

public interface BaseDao {
	/**
	 * 执行查询
	 * <em>用完后需调用该类的close()方法释放资源</em>
	 * @param sql 查询的SQL语句
	 * @param params 参数
	 * @return 查询结果集
	 */
	public ResultSet executeQuery(String sql, Object ... params);
	
	/**
	 * 执行增、删、该
	 * @param sql 要执行的SQL语句
	 * @param params 参数
	 * @return 受影响的行数
	 */
	public int executeUpdate(String sql, Object ... params);
	
	/**
	 * 获取数据库连接
	 */
	public void open();
	
	/**
	 * 释放资源
	 */
	public void close();
}
