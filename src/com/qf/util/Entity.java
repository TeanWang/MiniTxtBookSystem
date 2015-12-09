package com.qf.util;

/**
 * 客户端与服务器交互的介质
 * 
 * @author TeanWang
 *
 */
public class Entity implements java.io.Serializable {
	private static final long serialVersionUID = -968329478741470861L;
	private String command; // 请求的命令
	private Object Obj; // 客户端和服务器端交互的 数据对象
	private boolean isSuccess; // 判断命令是否执行成功
	private String info; // 携带信息

	public Entity(Object obj) {
		this.Obj = obj;
	}
	
	/**
	 * 创建带有命令的Entity对象
	 * @param cmd 命令
	 */
	public Entity(String cmd) {
		this.setCommand(cmd);
	}

	public Entity() {
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info =info;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getObj() {
		return Obj;
	}

	public void setObj(Object obj) {
		Obj = obj;
	}
}
