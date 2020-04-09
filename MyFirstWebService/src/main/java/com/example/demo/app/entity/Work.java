package com.example.demo.app.entity;

import java.time.LocalDateTime;


public class Work {
	private int id;
	private String work_name;
	private String number;
	private String money;
	private String gd_name;
	private String sg_name;
	private String contents;
	private LocalDateTime created;
	private String photoname;
	private String photoname2;
	
	
	public Work() {	
	}
	public String getPhotoname() {
		return photoname;
	}


	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	public String getPhotoname2() {
		return photoname2;
	}
	public void setPhotoname2(String photoname2) {
		this.photoname2 = photoname2;
	}
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getWork_name() {
		return work_name;
	}


	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getGd_name() {
		return gd_name;
	}


	public void setGd_name(String gd_name) {
		this.gd_name = gd_name;
	}


	public String getSg_name() {
		return sg_name;
	}


	public void setSg_name(String sg_name) {
		this.sg_name = sg_name;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
	}


	public LocalDateTime getCreated() {
		return created;
	}


	public void setCreated(LocalDateTime created) {
		this.created = created;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
