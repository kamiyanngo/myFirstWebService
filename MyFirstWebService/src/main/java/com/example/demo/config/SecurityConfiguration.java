//���O�C���F�؉��p
//�Q�l�ɂ����y�[�Whttps://takaxtech.com/2019/05/29/article311/
package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.example.demo.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private UserService userService;
	
	//�t�H�[���̒l�Ɣ�r����DB����擾�����p�X���[�h�͈Í�������Ă���̂Ńt�H�[���̒l���Í������邽�߂ɗ��p
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	
	/**
	 * �F�ݒ�𖳎����郊�N�G�X�g��ݒ�
	 * �ÓI���\�[�X(image,javascript,css)��F�����̑Ώۂ��珜�O����
	 */
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                            "/images/**",
                            "/css/**",
                            "/javascript/**"
                            );
    }
	
	/**
	 * �F�؁E�F�̏���ݒ肷��
	 * ��ʑJ�ڂ�URL�E�p�����[�^���擾����name�����̒l��ݒ�
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		   .authorizeRequests()
		        .anyRequest().authenticated()
		        .and()
		    .formLogin()
		        .loginPage("/login") //���O�C���y�[�W�̓R���g���[�����o�R���Ȃ��̂�ViewName�Ƃ̕R�t�����K�v
		        .loginProcessingUrl("/sign_in") //�t�H�[����SubmitURL�A����URL�փ��N�G�X�g��������ƔF�؏��������s�����
		        .usernameParameter("username") //���N�G�X�g�p�����[�^��name�����𖾎�
		        .passwordParameter("password")
		        .successForwardUrl("/work")
		        .failureUrl("/login?error")
		        .permitAll()
		        .and()
		    .logout()
		        .logoutUrl("/logout")
		        .logoutSuccessUrl("/login?logout")
		        .permitAll()
		        .and()
                // �ȉ��́AH2 Console�ɃA�N�Z�X���邽�߂ɕK�v
                .csrf().ignoringAntMatchers("/h2-console/**").and()
                .headers().frameOptions().sameOrigin();
	}
	
	/**
	 * �F�؎��ɗ��p����f�[�^�\�[�X���`����ݒ胁�\�b�h
	 * �����ł�DB����擾�������[�U����userDetailsService�փZ�b�g���邱�ƂŔF�؎��̔�r���Ƃ��Ă���
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		/*
		auth
		    .inMemoryAuthentication()
		        .withUser("user").password("password").roles("USER");
		*/
	}

}