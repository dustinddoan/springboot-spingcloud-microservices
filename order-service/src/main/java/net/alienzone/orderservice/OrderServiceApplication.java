package net.alienzone.orderservice;

import lombok.AllArgsConstructor;
import net.alienzone.orderservice.external.intercept.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients
@AllArgsConstructor
public class OrderServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	private final ClientRegistrationRepository clientRegistrationRepository;

	private final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate restTemplate
				= new RestTemplate();
		restTemplate.setInterceptors(
				Arrays.asList(
						new RestTemplateInterceptor(
								authorizedClientManager(clientRegistrationRepository
										,oAuth2AuthorizedClientRepository))));
		return restTemplate;
	}

	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository authorizedClientRepository) {

		OAuth2AuthorizedClientProvider authorizedClientProvider =
				OAuth2AuthorizedClientProviderBuilder.builder()
						.authorizationCode()
						.refreshToken()
						.clientCredentials()
						.build();

		DefaultOAuth2AuthorizedClientManager authorizedClientManager =
				new DefaultOAuth2AuthorizedClientManager(
						clientRegistrationRepository, authorizedClientRepository);
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

		return authorizedClientManager;
	}









}
