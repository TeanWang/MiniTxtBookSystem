package com.qf.biz;

import java.util.List;

import com.qf.dao.BookDao;
import com.qf.dao.impl.BookDaoImpl;
import com.qf.entity.Book;

/**
 * 小说业务逻辑类
 * 
 * @author TeanWang
 *
 */
public class BookBiz {
	private BookDao bookDao = new BookDaoImpl();
	
	/**
	 * 添加小说
	 * @param book 要添加的小说对象
	 * @return 是否添加成功
	 */
	public boolean addBook(Book book) {
		return bookDao.addBook(book);
	}
	
	/**
	 * 根据小说类别获取该类别下的所有小说
	 * @param type 小说分类
	 * @return 该分类下的所有小说，如果没有小说，则返回<strong>null</strong>
	 */
	public List<Book> getBookByType(String type) {
		List<Book> list = bookDao.getBookByType(type);
		return list.size() > 0 ? list : null;
	}
	
	/**
	 * 根据小说名称获取该小说
	 * @param name 小说名称
	 * @return 小说对象，如果没有则返回<strong>null</strong>
	 */
	public Book getBookByName(String name) {
		return bookDao.getBookByName(name);
	}
	
	/**
	 * 获取所有小说分类
	 * @return 小说分类集合
	 */
	public List<String> getAllTxtCategory() {
		return bookDao.getAllCategory();
	}

}
