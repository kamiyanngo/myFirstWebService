package com.example.demo.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.app.entity.LoginUser;

@Repository
public class LoginUserDao {

	@Autowired
	EntityManager em;

	/**
	 * フォームの入力値から該当するユーザを検索する.
	 * @param userName
	 * @return UserEntity、存在しないとき:Null
	 */
	public LoginUser findUser(String userName) {
		String query = "";
		query += "SELECT * ";
		query += "FROM user ";
		query += "WHERE username = :userName "; //setParameterで引数の値を代入できるようにNamedParameterを利用

		//EntityManagerで取得された結果はオブジェクトとなるので、LoginUser型へキャストが必要となる
		
			return (LoginUser)em.createNativeQuery(query, LoginUser.class).setParameter("userName", userName)
				.getSingleResult();
	
	}
}
