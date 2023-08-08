package com.boot.board230801.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Autowired
  private DataSource dataSource;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
//            .requestMatchers("/", "/home").permitAll()
//            허용되는 url
            .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/account/register")).permitAll()
//            홈 url 불필요
//            .requestMatchers(new AntPathRequestMatcher("/home")).permitAll()
//            .requestMatchers(new AntPathRequestMatcher("/navbar-static.css")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
            .anyRequest().authenticated()
        )
//        허가되지 않은 경우 url 이동
        .formLogin((form) -> form
//            .loginPage("/login")
            .loginPage("/account/login")
            .permitAll()
        )
        .logout((logout) -> logout.permitAll());

    return http.build();
  }

  //  Authentication : 로그인
  //  Authority : 권한
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
        .dataSource(dataSource)
//        패스워드 암호화 메소드를 스프링에서 관리
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery("select username,password,enabled "
            + "from user "
            + "where username = ?")
        .authoritiesByUsernameQuery(
            "select username,name "
                + "from user_role ur "
                + "inner join user u "
                + "on ur.user_id = u.id "
                + "inner join role r "
                + "on ur.role_id = r.id "
                + "where username = ?");
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}