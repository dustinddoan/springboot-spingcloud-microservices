package net.alienzone.orderservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2Config {
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(oktaClientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }



    private ClientRegistration oktaClientRegistration() {
        return ClientRegistration.withRegistrationId("internal-client")
                .clientId("0oab3xg3g3AeaQezA5d7")
                .clientSecret("QLsZ2QWRpzLr3ka0ec633JzdTozzu1vEEyZMaDIW_V17PL40ooQi2AJOBTKiH1xu")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("internal")
                .authorizationUri("https://dev-86709474.okta.com/oauth2/default/v1/authorize")
                .tokenUri("https://dev-86709474.okta.com/oauth2/default/v1/token")
                .userInfoUri("https://dev-86709474.okta.com/oauth2/default/v1/userinfo")
                .userNameAttributeName("username")
                .clientName("Okta")
                .build();
    }
}
