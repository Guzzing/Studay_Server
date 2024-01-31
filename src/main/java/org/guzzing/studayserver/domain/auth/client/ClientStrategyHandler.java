package org.guzzing.studayserver.domain.auth.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studayserver.global.common.auth.OAuth2Provider;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientStrategyHandler {

    private final Map<String, ClientStrategy> clients = new ConcurrentHashMap<>();

    public ClientStrategyHandler(WebClient webClient) {
        clients.put(OAuth2Provider.GOOGLE.name(), new ClientGoogleStrategy(webClient));
        clients.put(OAuth2Provider.KAKAO.name(), new ClientKakaoStrategy(webClient));
    }

    public ClientStrategy getClientStrategy(String clientName) {
        return clients.get(clientName);
    }

}
