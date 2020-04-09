package com.example.demo.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ���O�C�����[�U�̃��[�U���A�p�X���[�h���i�[���邽�߂�Entity
 * @author aoi
 *
 */
@Entity
@Table(name = "user")
public class LoginUser {
	
	@Column(name = "user_id")
	@Id
	private Long userId;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "password")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
