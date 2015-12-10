package com.qf.util;

/**
 * 常量类
 * @author TeanWang
 *
 */
public class Contants {
	/* 配置相关 */
	public static final String CONFIG_PATH = "config"; //配置文件的路径
	public static final String CONFIG_FILE_NAME = "minitxt.properties"; //配置文件的文件名称
	public static final String SOCKET_SERVER_IP = "socket.server.ip";  //服务器的IP地址
	public static final String SOCKET_SERVER_PORT = "socket.server.port"; //服务器Socket端口
	public static final String TXT_DOWNLOAD_PATH = "txt.download.path"; //小说下载路径
	
	/* 命令相关 */
	/**
	 * 登录命令
	 */
	public static final String COMMAND_LOGIN = "CMD_LOGIN";
	/**
	 * 注册命令
	 */
	public static final String COMMAND_REGISTER = "CMD_REGISTER";
	/**
	 * 显示小说分类命令
	 */
	public static final String COMMAND_SHOW_TXT_CATEGORY = "CMD_SHOW_TXT_CATEGORY";
	/**
	 * 获取指定分类下的所有小说命令
	 */
	public static final String COMMAND_SHOW_TXT_BY_CATEGORY = "CMD_SHOW_TXT_BY_CATEGORY";
	/**
	 * 上传命令
	 */
	public static final String COMMAND_UPLOAD = "CMD_UPLOAD";
	/**
	 * 下载命令
	 */
	public static final String COMMAND_DOWNLOAD = "CMD_DOWNLOAD";
	
}
