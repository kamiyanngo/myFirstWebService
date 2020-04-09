//ログイン認証回避用
//参考にしたページhttps://takaxtech.com/2019/05/29/article311/
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
	
	//フォームの値と比較するDBから取得したパスワードは暗号化されているのでフォームの値も暗号化するために利用
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	
	/**
	 * 認可設定を無視するリクエストを設定
	 * 静的リソース(image,javascript,css)を認可処理の対象から除外する
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
	 * 認証・認可の情報を設定する
	 * 画面遷移のURL・パラメータを取得するname属性の値を設定
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		   .authorizeRequests()
		        .anyRequest().authenticated()
		        .and()
		    .formLogin()
		        .loginPage("/login") //ログインページはコントローラを経由しないのでViewNameとの紐付けが必要
		        .loginProcessingUrl("/sign_in") //フォームのSubmitURL、このURLへリクエストが送られると認証処理が実行される
		        .usernameParameter("username") //リクエストパラメータのname属性を明示
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
                // 以下は、H2 Consoleにアクセスするために必要
                .csrf().ignoringAntMatchers("/h2-console/**").and()
                .headers().frameOptions().sameOrigin();
	}
	
	/**
	 * 認証時に利用するデータソースを定義する設定メソッド
	 * ここではDBから取得したユーザ情報をuserDetailsServiceへセットすることで認証時の比較情報としている
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