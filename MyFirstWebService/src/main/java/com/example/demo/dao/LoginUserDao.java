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
	 * �t�H�[���̓��͒l����Y�����郆�[�U����������.
	 * @param userName
	 * @return UserEntity�A���݂��Ȃ��Ƃ�:Null
	 */
	public LoginUser findUser(String userName) {
		String query = "";
		query += "SELECT * ";
		query += "FROM user ";
		query += "WHERE username = :userName "; //setParameter�ň����̒l�����ł���悤��NamedParameter�𗘗p

		//EntityManager�Ŏ擾���ꂽ���ʂ̓I�u�W�F�N�g�ƂȂ�̂ŁALoginUser�^�փL���X�g���K�v�ƂȂ�
		
			return (LoginUser)em.createNativeQuery(query, LoginUser.class).setParameter("userName", userName)
				.getSingleResult();
	
	}
}
