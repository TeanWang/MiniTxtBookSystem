package com.qf.entity;

import java.io.Serializable;

/**
 * 用户
 * 
 * @author TeanWang
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 3176035582832917357L;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;

	public User() {
	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
