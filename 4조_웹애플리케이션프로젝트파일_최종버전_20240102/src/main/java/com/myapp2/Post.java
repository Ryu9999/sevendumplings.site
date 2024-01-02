package com.myapp2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {

	private int listNum;
	private String title;
	private String content;
	private String id;
	private String file_dir;
	private Date wrTime;
	private Date editTime;
	private String fileName;
	private String file;

	public Post(int listNum, String title, String id, Date wrTime) {
		this.listNum = listNum;
		this.title = title;
		this.id = id;
		this.wrTime = wrTime;
	}

	public Post(int listNum, String title, String content, String id, String file_dir, Date editTime, Date wrTime,
			String file) {
		this.listNum = listNum;
		this.title = title;
		this.content = content;
		this.id = id;
		this.file_dir = file_dir;
		this.wrTime = wrTime;
		this.editTime = editTime;
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getWrTime() {
		return wrTime;
	}

	public void setWrTime(Date wrTime) {
		this.wrTime = wrTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile_dir() {
		return file_dir;
	}

	public void setFile_dir(String file_dir) {
		this.file_dir = file_dir;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getFormattedWrTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(wrTime);
	}
}
