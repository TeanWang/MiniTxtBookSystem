package com.qf.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.qf.biz.BookBiz;
import com.qf.biz.UserBiz;
import com.qf.entity.Book;
import com.qf.entity.User;
import com.qf.util.Contants;
import com.qf.util.Entity;

public class ServerThread implements Runnable {

	private Socket socket;

	private UserBiz ub = new UserBiz();
	private BookBiz bb = new BookBiz();

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream(); // 获取输入流 = 读取
			OutputStream os = socket.getOutputStream(); // 获取输出流 = 写

			ObjectInputStream ois = new ObjectInputStream(is);

			Entity cmd = (Entity) ois.readObject(); // 获取客户端发送过来的对象数据
			/* 下载 */
			if(cmd.getCommand().equals(Contants.COMMAND_DOWNLOAD)) {
				String type = cmd.getInfo(); // 获取要下载的小说名称
				Book book = bb.getBookByName(type);
				String fileName = book.getFileName(); // 获取小说的文件名称
				// 获取小说的存放路径
				String path = "data/" + fileName.substring(0, 6) + File.separator + fileName;
				System.out.println(path);
				FileInputStream fis = new FileInputStream(path); // 要下载的文件流
				BufferedInputStream bis = new BufferedInputStream(fis);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] b = new byte[1024 * 1024];
				int len = -1;
				while((len = bis.read(b)) != -1) {
					baos.write(b, 0, len);
				}
				BufferedOutputStream bos = new BufferedOutputStream(os);
				bos.write(baos.toByteArray());
				
				bos.flush();
				bos.close();
				baos.close();
				bis.close();
				fis.close();
				ois.close();
				os.close();
				is.close();
				return;
			} else if(Contants.COMMAND_UPLOAD.equals(cmd.getCommand())) { // 上传
				
			}
			
			/* 响应服务器 */
			ObjectOutputStream oos = new ObjectOutputStream(os);
			Entity command = executeCommand(cmd);

			oos.writeObject(command);
			oos.flush();
			oos.close();
			ois.close();
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 执行命令
	 * 
	 * @param cmd
	 */
	public Entity executeCommand(Entity entity) {
		String command = entity.getCommand(); // 获取命令
		if (command.equals(Contants.COMMAND_LOGIN)) { // 登录
			return doLogin(entity);
		} else if (command.equals(Contants.COMMAND_REGISTER)) { // 注册
			return doRegister(entity);
		} else if (command.equals(Contants.COMMAND_SHOW_TXT_CATEGORY)) { // 显示小说分类
			return doShowTxtCategory(entity);
		} else if (command.equals(Contants.COMMAND_SHOW_TXT_BY_CATEGORY)) { // 显示指定分类下的小说
			return doShowAllTxtByCategory(entity);
		}
		return new Entity();
	}

	// 显示指定分类下的所有小说
	public Entity doShowAllTxtByCategory(Entity entity) {
		List<Book> list = bb.getBookByType(entity.getInfo());
		entity.setObj(list);
		entity.setIsSuccess(true);
		// 日志
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.print(date + "\t=>\t");
		// 获取客户端的IP地址
		InetAddress ia = socket.getInetAddress();
		System.out.println(ia.getHostAddress() + "执行命令：" + entity.getCommand());
		return entity;
	}

	// 显示小说分类
	public Entity doShowTxtCategory(Entity entity) {
		List<String> list = bb.getAllTxtCategory();
		entity.setObj(list);
		entity.setIsSuccess(true);
		// 日志
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.print(date + "\t=>\t");
		// 获取客户端的IP地址
		InetAddress ia = socket.getInetAddress();
		System.out.println(ia.getHostAddress() + "执行命令：" + entity.getCommand());
		return entity;
	}

	// 注册
	public Entity doRegister(Entity entity) {
		String json = entity.getInfo(); // 获取客户端发送过来的JSON数据
		// 解析该JSON数据，获取用户名和密码
		String name = "";
		String pwd = "";
		try {
			JSONObject obj = new JSONObject(json);
			name = obj.getString("name");
			pwd = obj.getString("pwd");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// 判断是否注册成功
		if (ub.register(name, pwd)) {
			entity.setIsSuccess(true);
			entity.setInfo("注册成功！");
			// 日志
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.print(date + "\t=>\t");
			// 获取客户端的IP地址
			InetAddress ia = socket.getInetAddress();
			System.out.println(ia.getHostAddress() + "注册成功");
		} else {
			entity.setIsSuccess(false);
			entity.setInfo("注册失败！");
			// 日志
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.print(date + "\t=>\t");
			// 获取客户端的IP地址
			InetAddress ia = socket.getInetAddress();
			System.out.println(ia.getHostAddress() + "注册失败了");
		}
		return entity;
	}

	// 登录
	public Entity doLogin(Entity entity) {
		User user = (User) entity.getObj();
		boolean canLogin = ub.login(user.getName(), user.getPassword());
		if (canLogin) {
			entity.setIsSuccess(true);
			entity.setInfo("登录成功！");
			// 日志
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.print(date + "\t=>\t");
			// 获取客户端的IP地址
			InetAddress ia = socket.getInetAddress();
			System.out.println(ia.getHostAddress() + "登录成功");
		} else {
			entity.setIsSuccess(false);
			entity.setInfo("登录失败！");
			// 日志
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.print(date + "\t=>\t");
			// 获取客户端的IP地址
			InetAddress ia = socket.getInetAddress();
			System.out.println(ia.getHostAddress() + "登录失败");
		}
		return entity;
	}

}
