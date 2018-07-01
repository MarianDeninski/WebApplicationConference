package com.teamthree.conferencescheduler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
    This class defines our Security module.
    Overall, the configuration annotation will tell Spring that this is a configuration class,
    and the rest of the annotations will set different settings for it.
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /*
        Making Spring to automatically change the default password
        encoder to BCrypt.
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*
        This method defines what permissions are needed to access out application.

        This code tells the authentication module, that every page can be accessed by every user.
        Then it tells us that the login request should be expected at the "/login" route.
        The parameter for login will be "email" and the parameter for password will be "password".
        The logout will lead to "/login?logout" and if there is any error with the permissions,
        we should receive view that tells us that we don’t have access.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/error/403")
                .and()
                .csrf();
    }
}
