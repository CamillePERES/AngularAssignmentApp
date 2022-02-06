package com.example.assignmentapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /*@Autowired
  private JwtTokenProvider jwtTokenProvider;*/

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    corsConfiguration.setAllowedOrigins(List.of("*"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
    //corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setExposedHeaders(List.of("Authorization"));

    // Disable CSRF (cross site request forgery)

    http.cors().configurationSource(request -> corsConfiguration).and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/users/signin", "/users/signup", "/users/savepic/**", "/users/pic/**",  "/courses/pic/**").permitAll()
            .anyRequest().authenticated()
            .and()
            //.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Entry points
   /* http.authorizeRequests()//
        .antMatchers("/users/signin").permitAll()//
        .antMatchers("/users/signup").permitAll()//
        //.antMatchers(HttpMethod.GET, "/courses").permitAll()//
        // Disallow everything else..
        .anyRequest().authenticated();*/

    //http.csrf().disable().cors().configurationSource(request -> corsConfiguration);

    // If a user try to access a resource without having enough permissions
    //http.exceptionHandling().accessDeniedPage("/login");

    // Apply JWT
    //http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    // Optional, if you want to test the API from a browser
    // http.httpBasic();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public")//
        
        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
    return new JwtTokenFilter();
  }

}
