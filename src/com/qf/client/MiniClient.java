package com.qf.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.List;
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
		System.out.println("欢 迎 使 用 在 线 迷 你 TXT 小 说 管 理 系 统");
		System.out.println("-----------------------------------------------");
		System.out.println("> 1.登录\n> 2.注册\n> 3.退出");
		System.out.println("-----------------------------------------------");
		System.out.print("请选择：");
		Scanner sc = new Scanner(System.in);
		boolean isContinue = true;
		while (isContinue) {
			int choose = 0;
			try {
				choose = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				sc.nextLine();
			}
			isContinue = false;
			switch (choose) {
			case 1:
				// 执行登录窗口
				showLoginWindow();
				break;
			case 2:
				// 执行注册窗口
				showRegisterWindow();
				break;
			case 3:
				// 退出程序
				System.out.println("谢谢使用！");
				System.exit(0);
				break;
			default:
				System.out.print("请输入有效数字：");
				isContinue = true;
				break;
			}
		}
		sc.close();
	}

	/**
	 * 用户注册窗口，从控制台接收用户输入的用户名和密码，然后用正则判断是否符合要求，
	 * 如果符合要求的用户名和密码封装成JSON对象发送到服务器端
	 */
	public void showRegisterWindow() {
		Scanner sc = new Scanner(System.in);
		showBoLang("当前操作：用户注册");
		System.out.print("请输入登录名（数字字母下划线组成4~10位）：");
		String userName = sc.next();
		System.out.print("请输入密码（数字字母下划线组成4~10位）：");
		String userPwd = sc.next();
		// 用正则验证是否合法
		if(userName.matches("\\w{4,10}") && userPwd.matches("\\w{4,10}")) {
			String json = "{name:'" + userName + "',pwd:'" + userPwd +"'}"; // 把用户名和密码封装成JSON发送给服务器
			Entity entity = new Entity(Contants.COMMAND_REGISTER); // 创建带有注册命令的Entity对象
			entity.setInfo(json);
			/* 发送给服务器，并获取服务器响应的信息 */
			if (getConnection()) {
				sendServerCommand(entity);
				entity = getServerCommand();
				closeSocket(); // 释放
				// 打印注册结果信息
				showXingHao(entity.getInfo());
				if (entity.getIsSuccess()) { // 判断是否注册成功
					showLoginWindow(); // 进入登录界面
				} else {
					showRegisterWindow(); // 注册失败再次进入注册页面
				}
			}
		} else {
			System.out.println("!-> 您输入的用户名或密码不符合要求！");
			showRegisterWindow();
		}
		sc.close();
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
		// 获取用户对象
		User user = new User(userName, userPwd);
		// 封装要发送给服务器的命令对象
		Entity cmd = new Entity(user);
		cmd.setCommand(Contants.COMMAND_LOGIN);

		if (getConnection()) {
			sendServerCommand(cmd);
			cmd = getServerCommand();
			closeSocket(); // 释放
			// 打印登录结果信息
			showXingHao(cmd.getInfo());
			if (cmd.getIsSuccess()) { // 判断是否登录成功
				// 显示小说列表
				showTxtCategoryWindow();
			} else {
				showLoginWindow(); // 重新登录
			}
		}
		sc.close();
	}

	/**
	 * 显示小说分类
	 */
	public void showTxtCategoryWindow() {
		Entity entity = new Entity(Contants.COMMAND_SHOW_TXT_CATEGORY);
		if(getConnection()) {
			sendServerCommand(entity);
			entity = getServerCommand();
			closeSocket();
			// 显示服务器响应信息
			if(entity.getIsSuccess()) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>)entity.getObj();
				String msg = "";
				for (int i = 0; i < list.size() - 1; i++) {
					msg += (i + 1) + ". " + list.get(i) + "\n";
				}
				showBoLang(msg + list.size() + ". " + list.get(list.size() - 1));
				System.out.print("请选择：");
				Scanner sc = new Scanner(System.in);
				boolean flag = true;
				int choose = 0;
				while(flag) {
					try {
						choose = sc.nextInt();
						if(choose < 1 || choose > list.size()) {
							throw new Exception();
						}
						flag = false;
					} catch (Exception e) {
						sc.nextLine();
						System.out.print("!-> 输入有误，请输入有效序号：");
					}
				}
				String type = list.get(choose - 1);
				System.out.println("您选择了：" + type);
				sc.close();
			}
		} else {
			System.out.println("!-> 链接服务器错误，请稍后再试！");
		}
	}

	/**
	 * 建立与服务器的连接
	 */
	public boolean getConnection() {
		ConfigManager cm = ConfigManager.getInstance();
		boolean isOK = false;
		// 获取服务器的IP
		String ip = cm.getValue(Contants.SOCKET_SERVER_IP);
		// 获取端口号
		int port = Integer.parseInt(cm.getValue(Contants.SOCKET_SERVER_PORT));
		try {
			clientSocket = new Socket();
			SocketAddress address = new InetSocketAddress(ip, port);
			clientSocket.connect(address, 5000); // 连接，并且设置超时时间为5s
			isOK = true;
		} catch (UnknownHostException e) {
			System.out.println("!-> 链接服务器失败，未知主机：" + ip + ":" + port);
		} catch (IOException e) {
			System.out.println("!-> 服务器链接异常，请联系管理员！");
		}
		return isOK;
	}

	/**
	 * 发送命令到服务器
	 * 
	 * @param cmd
	 *            要发送的命令对象
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
	 * 
	 * @return
	 */
	public Entity getServerCommand() {
		Entity cmd = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			cmd = (Entity) ois.readObject();

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
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(txt);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	public void showXingHao(String txt) {
		System.out.println("***********************************************");
		System.out.println(txt);
		System.out.println("***********************************************");
	}

}
