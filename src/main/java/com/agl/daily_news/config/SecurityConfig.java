package com.agl.daily_news.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  JwtFilter jwtFilter;
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // disable csrf
    http.csrf(csrf -> {
      csrf.disable();
    });

    // session management
    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    });

    // authorize request
    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/").permitAll().requestMatchers("/api-docs").permitAll()
          .requestMatchers("/api-docs.yaml").permitAll().requestMatchers("/swagger-ui/*").permitAll().requestMatchers("/api-docs/swagger-config").permitAll()
          .requestMatchers(HttpMethod.POST, "api/users/*").permitAll()
          .requestMatchers("/admin/**").hasAuthority("ADMIN")
          .requestMatchers("/api/news/create/**").hasAuthority("CREATOR")
          .requestMatchers("/api/comments/**").hasAnyAuthority("CREATOR", "ADMIN", "USER")
          .anyRequest().fullyAuthenticated();
    });

    // set authentication provider
    http.authenticationProvider(authenticationProvider());

    // set filter
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    // basic auth
    // http.httpBasic();

    return http.build();
  }

  /*
   * digunakan untuk mengautentikasi user yg mau akses request dan/atau login
   */
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) 
    throws Exception {
      return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  /*
   * DaoAuthenticationProvider: provider untuk mencari email dan match-kan
   * passwordnya
   */
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

}
