package com.siriusxi.cloud.infra.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * This SecurityConfig class allows
 * <pre>StoreServiceApplication</pre> to act as oauth2 Resource Server.
 *
 * @author Mohamed Taman
 * @since v5.0, codename: Protector
 * @version v1.0
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http)  {
      http.csrf(withDefaults())
              .authorizeExchange(exchange -> exchange
                      .pathMatchers("/headerrouting/**").permitAll()
                      .pathMatchers("/actuator/**").permitAll()
                      .pathMatchers("/eureka/**").permitAll()
                      .pathMatchers("/oauth/**").permitAll()
                      .anyExchange().authenticated())
              .oauth2ResourceServer(server -> server
                      .jwt());

    return http.build();
    }

}
