package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //基于内存的用户存储
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("123456").roles("ADMIN","USER")
//                .and()
//                .withUser("cc").password("123456").roles("USER");
//
//    }

    //基于JDBC的用户存储
//    @Autowired
//    DataSource dataSource;
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.jdbcAuthentication().dataSource(dataSource)
//        .usersByUsernameQuery("select username,password,enable from users where username=?")
//        .authoritiesByUsernameQuery("select username,authority from userauthorities where username=?")
//        .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

    //基于LDAP作为后端的用户存储
//    @Autowired
//    DataSource dataSource;
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.ldapAuthentication()
//                .userSearchFilter("(uid)={0}")
//                .groupSearchFilter("member={0}");
//    }


    @Bean
    public PasswordEncoder encoder(){
        return new StandardPasswordEncoder("53cr3t");
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/design","/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/","/**").access("permitAll")
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/");
    }

}
