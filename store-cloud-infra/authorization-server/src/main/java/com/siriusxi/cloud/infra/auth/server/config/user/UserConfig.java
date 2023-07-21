package com.siriusxi.cloud.infra.auth.server.config.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/** For configuring the end users recognized by this Authorization Server */
@Configuration
class UserConfig {

  private final PasswordEncoder encoder =
          PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(requests -> requests
            .requestMatchers("/actuator/**")
            .permitAll()
            .requestMatchers("/.well-known/jwks.json")
            .permitAll()
            .anyRequest()
            .authenticated())
            .httpBasic(withDefaults())
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers(request -> "/introspect".equals(request.getRequestURI())));
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {

    return new InMemoryUserDetailsManager(
        User.builder()
            .username("taman")
            .password(encoder.encode("password"))
            .roles("USER")
            .build());
  }
}
