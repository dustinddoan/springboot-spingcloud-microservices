package net.alienzone.orderservice.external.intercept;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
@Configuration
@AllArgsConstructor
public class OAuthRequestInterceptor implements RequestInterceptor {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Override
    public void apply(RequestTemplate template) {

        String token = authorizedClientManager
                .authorize(OAuth2AuthorizeRequest.withClientRegistrationId("internal-client")
                        .principal("internal")
                        .build())
                .getAccessToken().getTokenValue();

        template.header("Authorization", "Bearer " + token);
    }
}
