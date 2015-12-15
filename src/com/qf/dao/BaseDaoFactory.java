package com.qf.dao;

import com.qf.dao.impl.BaseDaoMySQLImpl;
import com.qf.dao.impl.BaseDaoSQLiteImpl;

/**
 * BaseDao工厂
 * @author TeanWang
 *
 */
public class BaseDaoFactory {
	
	/**
	 * 获取BaseDao对象
	 * @param id 0：MySQL  1：SQLite3
	 * @return 具体数据库的BaseDao对象
	 */
	public static BaseDao getInstance(int id) {
		switch(id) {
		case 0: // MySQL
			return new BaseDaoMySQLImpl();
		case 1: // SQLite3
			return new BaseDaoSQLiteImpl();
		
		}
		return null;
	}

}
