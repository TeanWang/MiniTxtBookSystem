package com.qf.entity;

import java.io.Serializable;

/**
 * 小说
 * @author TeanWang
 *
 */
public class Book implements Serializable {
	private static final long serialVersionUID = -3179912701707092393L;

	private String name; // 小说名称
	private String author; // 小说作者
	private String summary; // 摘要
	private String fileName; // 小说对应的文件名称
	private String type; // 小说的类型

	public Book() {}

	public Book(String name, String author, String summary, String fileName, String type) {
		this.name = name;
		this.author = author;
		this.summary = summary;
		this.fileName = fileName;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
