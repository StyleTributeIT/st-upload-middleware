package com.styletribute.upload.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.styletribute.authenticate.filter.CorsFilter;
import com.styletribute.authenticate.filter.StyleAuthenticationFilter;
import com.styletribute.authenticate.provider.StyleAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.styletribute.authenticate"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired 
    private AuthenticationManager authenticationManager;
    @Autowired
    private StyleAuthenticationProvider styleAuthenticationProvider;
    
    @Value("${styletribute.origin.url}")
    private String originUrl;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(styleAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        StyleAuthenticationFilter filter = new StyleAuthenticationFilter("/secureuploader/**");
        CorsFilter corsFilter = new CorsFilter(originUrl);
        
        filter.setAuthenticationManager(authenticationManager);
        http
        .csrf()
        .disable()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .antMatchers("/uploader/**").permitAll()
        .anyRequest()
        .authenticated()
        .and()      
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
