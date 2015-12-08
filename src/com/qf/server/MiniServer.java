package com.qf.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.qf.util.ConfigManager;
import com.qf.util.Contants;



/**
 * 服务器类
 */
public class MiniServer {
	
	ConfigManager cm = null; //配置文件读取对象
	ServerSocket ss = null; 
	
	public void startServer() {
		try {
			cm = ConfigManager.getInstance();
			int port = Integer.parseInt(cm.getValue(Contants.SOCKET_SERVER_PORT));//获取端口号
			ss = new ServerSocket(port);
			while(true) {
				Socket socket = ss.accept();
				
				
				
				//采用线程，每次请求一个线程
				Thread t = new Thread(new ServerThread(socket));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new MiniServer().startServer();
	}
	

}
