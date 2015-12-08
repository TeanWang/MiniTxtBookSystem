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
	private String resultInfo; // 返回的信息

	public Entity(Object obj) {
		this.Obj = obj;
	}

	public Entity() {
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
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
