package com.qf.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.qf.dao.UserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.User;
import com.qf.util.Contants;
import com.qf.util.Entity;



public class ServerThread implements Runnable {
	
	private Socket socket;
	
	private UserDao ud = new UserDaoImpl();
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();  //获取输入流=读取
			OutputStream os = socket.getOutputStream(); //获取输入流 = 写
			
			ObjectInputStream ois = new ObjectInputStream(is);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			Entity cmd = (Entity)ois.readObject();  //获取客户端发送过来的对象数据
			Entity command = executeCommand(cmd);
			oos.writeObject(command);
			oos.flush();
			oos.close();
			ois.close();
			os.close();
			is.close();
			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 执行命令
	 * @param cmd
	 */
	public Entity executeCommand(Entity cmd) {
		String command = cmd.getCommand(); //获取命令
		if(command.equals(Contants.COMMAND_LOGIN)) {
			//执行登录的操作
			User user = (User)cmd.getObj();
			boolean canLogin = true;//= ud.canLogin(user);
			if(canLogin) {
				cmd.setIsSuccess(true);
				cmd.setResultInfo("登录成功！");
				//日志
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				System.out.print(date + "\t=>\t");
				//获取客户端的IP地址
				InetAddress ia = socket.getInetAddress();
				System.out.println(ia.getHostAddress() + "登录成功");
			} else {
				cmd.setIsSuccess(false);
				cmd.setResultInfo("登录失败！");
				//日志
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				System.out.print(date + "\t=>\t");
				//获取客户端的IP地址
				InetAddress ia = socket.getInetAddress();
				System.out.println(ia.getHostAddress() + "登录失败了");
			}
			return cmd;
		} else if(command.equals(Contants.COMMAND_REGISTER)) {
			//执行注册操作
		}
		return new Entity();
	}
	

}
