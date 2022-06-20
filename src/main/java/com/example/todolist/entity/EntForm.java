package com.example.todolist.entity;

public class EntForm{
	
	private int id;

	private String title;

	private String detail;

	private boolean done;

	public EntForm() {};

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public Boolean getDone() {
		return done;
	}
}
