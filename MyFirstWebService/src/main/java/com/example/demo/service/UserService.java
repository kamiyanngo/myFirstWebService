package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LoginUserDao;
import com.example.demo.app.entity.LoginUser;


@Service
public class UserService implements UserDetailsService{

    //DB���烆�[�U�����������郁�\�b�h�����������N���X
    @Autowired
    private LoginUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	
        LoginUser user = userDao.findUser(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User" + userName + "was not found in the database");
        }
        //�����̃��X�g
        //Admin��User�Ȃǂ����݂��邪�A����͗��p���Ȃ��̂�USER�݂̂����Őݒ�
        //�����𗘗p����ꍇ�́ADB��Ō����e�[�u���A���[�U�����e�[�u�����쐬���Ǘ����K�v
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantList.add(authority);

        //rawData�̃p�X���[�h�͓n�����Ƃ��ł��Ȃ��̂ŁA�Í���
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //UserDetails�̓C���^�t�F�[�X�Ȃ̂�User�N���X�̃R���X�g���N�^�Ő����������[�U�I�u�W�F�N�g���L���X�g
        UserDetails userDetails = (UserDetails)new User(user.getUserName(), encoder.encode(user.getPassword()),grantList);

        return userDetails;
    }

}


