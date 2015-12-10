package com.qf.dao;

import java.util.List;

import com.qf.entity.Book;

/**
 * 小说数据访问接口
 * 
 * @author TeanWang
 *
 */
public interface BookDao {
	
	/**
	 * 添加小说
	 * @param book 要添加的小说对象
	 * @return 添加是否成功
	 */
	boolean addBook(Book book);
	
	/**
	 * 根据小说分类获取该分类下所有的小说
	 * @param type 小说分类
	 * @return 获取的小说集合
	 */
	List<Book> getBookByType(String type);
	
	/**
	 * 根据小说名称获取小说
	 * @param name 小说名称
	 * @return 获取的小说对象，如果没有则返回<strong>null</strong>
	 */
	Book getBookByName(String name);
	
	/**
	 * 获取所有小说分类
	 * @return 小说分类集合
	 */
	List<String> getAllCategory();

}
