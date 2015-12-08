package com.qf.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.qf.entity.User;
import com.qf.util.ConfigManager;
import com.qf.util.Contants;
import com.qf.util.Entity;


public class MiniClient {
	public Socket clientSocket = null; // 客户端socket
	
	/**
	 * 客户端主程序菜单
	 */
	public void startMenu() {
		System.out.println("欢 迎 使 用 在线迷 你TXT小说管 理 器");
		System.out.println("-------------------------------------");
		System.out.println("1.登录\n2.注册\n3.退出");
		System.out.println("-------------------------------------");
		System.out.print("请选择：");
		Scanner sc = new Scanner(System.in);
		boolean isContinue = true;
		while(isContinue) {
			int choose = 0;
			try {
				choose = sc.nextInt();
				sc.nextLine();
			}catch (InputMismatchException e) {
				sc.nextLine();
			}
			isContinue = false;
			switch (choose) {
			case 1:
				//执行登录窗口
				showLoginWindow();
				break;
			case 2:
				//执行注册窗口
				System.out.println("注册");
				break;
			case 3:
				//退出程序
				System.out.println("谢谢使用！");
				System.exit(0);
				break;
			default:
				System.out.print("请输入有效数字：");
				isContinue = true;
				break;
			}
		}
	}
	
	/**
	 * 用户登录窗口
	 */
	public void showLoginWindow() {
		Scanner sc = new Scanner(System.in);
		showBoLang("当前操作：用户登录");
		
		System.out.print("请输入登录名：");
		String userName = sc.next();
		System.out.print("请输入密码：");
		String userPwd = sc.next();
		//获取用户对象
		User user = new User(userName, userPwd);
		//封装要发送给服务器的命令对象
		Entity cmd = new Entity(user);
		cmd.setCommand(Contants.COMMAND_LOGIN);

		if(getConnection()) {
			sendServerCommand(cmd);
			cmd = getServerCommand();
			//打印登录结果信息
			showXingHao(cmd.getResultInfo());
			if(cmd.getIsSuccess()) { //判断是否登录成功
				//显示小说列表 --   未完待续
				closeSocket();
			} else {
				showLoginWindow();
			}
		}
		
	}
	
	
	

	/**
	 * 建立与服务器的连接
	 */
	public boolean getConnection() {
		boolean isOK = false;
		// 获取服务器的ip
		String ip = ConfigManager.getInstance().getValue(
				Contants.SOCKET_SERVER_IP);
		// 获取端口号
		int port = Integer.parseInt(ConfigManager.getInstance().getValue(
				Contants.SOCKET_SERVER_PORT));
		try {
			clientSocket = new Socket(ip, port);
			isOK = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isOK;
	}
	
	/**
	 * 发送命令到服务器
	 * @param cmd 要发送的命令对象
	 */
	public void sendServerCommand(Entity cmd) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到服务器响应的命令对象
	 * @return
	 */
	public Entity getServerCommand() {
		Entity cmd = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			cmd =  (Entity)ois.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cmd;
	}
	
	/**
	 * 关闭socket
	 */
	public void closeSocket() {
		try {
			

			clientSocket.shutdownOutput();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MiniClient mc = new MiniClient();
		mc.startMenu();
	}
	
	
	public void showBoLang(String txt) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(txt);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public void showXingHao(String txt) {
		System.out.println("*************************************");
		System.out.println(txt);
		System.out.println("*************************************");
	}
	
}
