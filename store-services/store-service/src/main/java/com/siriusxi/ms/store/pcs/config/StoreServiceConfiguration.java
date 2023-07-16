package com.siriusxi.ms.store.pcs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static java.util.Collections.emptyList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class StoreServiceConfiguration {

  @Value("${api.common.version}")
  private String apiVersion;

  @Value("${api.common.title}")
  private String apiTitle;

  @Value("${api.common.description}")
  private String apiDescription;

  @Value("${api.common.termsOfServiceUrl}")
  private String apiTermsOfServiceUrl;

  @Value("${api.common.license}")
  private String apiLicense;

  @Value("${api.common.licenseUrl}")
  private String apiLicenseUrl;

  @Value("${api.common.contact.name}")
  private String apiContactName;

  @Value("${api.common.contact.url}")
  private String apiContactUrl;

  @Value("${api.common.contact.email}")
  private String apiContactEmail;

  /**
   * Will exposed on $HOST:$PORT/swagger-ui.html
   *
   * @return Docket swagger configuration
   */
  @Bean
  public GroupedOpenApi apiDocumentation() {

    return GroupedOpenApi.builder().build();
  }

  @Bean
  @LoadBalanced
  public WebClient.Builder loadBalancedWebClientBuilder() {
    return WebClient.builder();
  }
}
