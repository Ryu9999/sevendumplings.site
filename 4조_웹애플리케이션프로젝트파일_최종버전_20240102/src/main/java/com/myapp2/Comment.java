package com.myapp2;

import java.util.Date;

public class Comment {
	private int id;
	private String username;
	private String commentContent;
	private Date commentTime;

	public Comment(int id, String username, String commentContent, Date commentTime) {
		this.id = id;
		this.username = username;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
	}

	public Comment() {

		this.username = "";
		this.commentContent = "";
		this.commentTime = new Date();
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
}
