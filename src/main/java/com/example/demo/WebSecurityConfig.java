package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SessionRegistry sessionRegistry() {			
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
        
                .antMatchers("http://**", "https://**", "/resources/**", "/user/registration", "/", "/user/allarticle",
                        "/user/showallproducts", "/user/allcategory","/employee/new","/client/new")
                .permitAll()
                .antMatchers("/supplier/dashboard","/supplier/new","/supplier/detail/**","/supplyOrder/edit/**","/supplyOrder/add","/supplier/rawMaterial/add").hasAnyAuthority("Admin","Supplier","Employee")
                .antMatchers("/check/user/**").permitAll()
                .antMatchers("/client/dashboard","/client/detail","/client/email/add","/client/edit","/client/order/new","/client/order/{clientOrderId}","/clientOrder/add","/client/order/remark","/product/show").hasAnyAuthority("Client","Admin")
                .antMatchers("/employee/new","/employee/contacts/add").permitAll()
                .antMatchers("/employee/detail/**","/employee/contacts/add","/employee/edit","/plant/show","/plant/new","/plant/**","/product/show","/product/new","/product/**").hasAnyAuthority("Admin","Employee","Client")
                .antMatchers("/admin/dashboard","/supplier/show").hasAnyAuthority("Admin")
                .antMatchers(HttpMethod.POST,"/client/dashboard","/client/detail","/client/email/add","/client/edit","/client/order/new","/client/order/{clientOrderId}","/clientOrder/add","/client/order/remark").hasAuthority("Client")
                .antMatchers("/employee/detail").hasAuthority("Employee").antMatchers("/user/**").hasAuthority("USER").antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/supplier/**")
                .hasAuthority("SUPPLIER").anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/detail")
                .and().logout().permitAll();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}