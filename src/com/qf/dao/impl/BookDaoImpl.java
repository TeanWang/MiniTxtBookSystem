package com.qf.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qf.dao.BookDao;
import com.qf.dao.DBHelper;
import com.qf.entity.Book;

/**
 * 小说数据访问实现类
 * 
 * @author TeanWang
 *
 */
public class BookDaoImpl implements BookDao {
	
	private DBHelper db = new DBHelper();
	
	@Override
	/**
	 * 添加小说
	 * @param book 要添加的小说对象
	 * @return 添加是否成功
	 */
	public boolean addBook(Book book) {
		int result = db.executeUpdate("INSERT INTO tb_book VALUES (?,?,?,?,?)", book.getName(), book.getAuthor(), book.getSummary(), book.getFileName(), book.getType());
		return result > 0 ? true : false;
	}

	/**
	 * 根据小说分类获取该分类下所有的小说
	 * @param type 小说分类
	 * @return 获取的小说集合
	 */
	@Override
	public List<Book> getBookByType(String type) {
		List<Book> list = new ArrayList<Book>();
		ResultSet rs = db.executeQuery("SELECT name,author,summary,filename,type FROM tb_book WHERE type=?", type);
		Book book;
		try {
			while(rs.next()) {
				book = new Book();
				book.setName(rs.getString("name"));
				book.setAuthor(rs.getString("author"));
				book.setSummary(rs.getString("summary"));
				book.setFileName(rs.getString("filename"));
				book.setType(rs.getString("type"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return list;
	}

	@Override
	/**
	 * 根据小说名称获取小说
	 * @param name 小说名称
	 * @return 获取的小说对象，如果没有则返回<strong>null</strong>
	 */
	public Book getBookByName(String name) {
		ResultSet rs = db.executeQuery("SELECT name,author,summary,filename,type FROM tb_book WHERE name=?", name);
		Book book = null;
		try {
			if(rs.next()) {
				book = new Book();
				book.setName(rs.getString("name"));
				book.setAuthor(rs.getString("author"));
				book.setSummary(rs.getString("summary"));
				book.setFileName(rs.getString("filename"));
				book.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return book;
	}

	/**
	 * 获取所有小说分类
	 * @return 小说分类集合
	 */
	@Override
	public List<String> getAllCategory() {
		List<String> list = new ArrayList<String>();
		ResultSet rs = db.executeQuery("SELECT type FROM tb_book GROUP BY type");
		try {
			while(rs.next()) {
				list.add(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return list;
	}

}
