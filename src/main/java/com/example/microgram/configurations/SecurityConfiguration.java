package com.example.microgram.configurations;

import com.example.microgram.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.httpBasic();
//        http.cors().disable();
        http.formLogin().disable().logout().disable();
        http.csrf().disable();

        http.authorizeRequests()
//                .antMatchers("/post/**")
//                .fullyAuthenticated()

                .antMatchers(HttpMethod.GET,"/user/**")
                .fullyAuthenticated()

//                .antMatchers(HttpMethod.POST,"/comment/**")
//                .fullyAuthenticated()

                .antMatchers(HttpMethod.POST,"/like/**")
                .fullyAuthenticated()

                .antMatchers(HttpMethod.POST,"/follow/**")
                .fullyAuthenticated()

                .anyRequest()
                .permitAll()
        ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }
}