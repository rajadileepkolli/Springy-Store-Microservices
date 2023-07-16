package com.siriusxi.cloud.infra.eds.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

  private final String username;
  private final String password;

  public SecurityConfig(
      @Value("${app.eureka.user}") String username,
      @Value("${app.eureka.pass}") String password) {
    this.username = username;
    this.password = "{noop}".concat(password);
  }

  @Bean
  InMemoryUserDetailsManager inMemoryAuthManager() throws Exception {
    return new InMemoryUserDetailsManager(User.builder().username(username).build());
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            // Disable CRCF to allow services to register themselves with Eureka
            .csrf(withDefaults())
            .authorizeRequests(requests -> requests
                    .anyRequest().authenticated())
            .httpBasic(withDefaults());
    return http.build();
  }
}
