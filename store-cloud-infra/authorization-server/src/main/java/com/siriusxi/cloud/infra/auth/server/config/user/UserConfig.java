package com.siriusxi.cloud.infra.auth.server.config.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.security.config.Customizer.withDefaults;

/** For configuring the end users recognized by this Authorization Server */
@Configuration
class UserConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder encoder =
          PasswordEncoderFactories.createDelegatingPasswordEncoder();

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(requests -> requests
            .antMatchers("/actuator/**")
            .permitAll()
            .mvcMatchers("/.well-known/jwks.json")
            .permitAll()
            .anyRequest()
            .authenticated())
            .httpBasic(withDefaults())
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers(request -> "/introspect".equals(request.getRequestURI())));
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {

    return new InMemoryUserDetailsManager(
        User.builder()
            .username("taman")
            .password(encoder.encode("password"))
            .roles("USER")
            .build());
  }
}
