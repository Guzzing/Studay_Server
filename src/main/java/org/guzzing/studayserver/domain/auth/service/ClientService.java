package org.guzzing.studayserver.domain.auth.service;

import java.util.Optional;
import org.guzzing.studayserver.domain.auth.client.ClientProxy;
import org.guzzing.studayserver.domain.auth.client.ClientStrategy;
import org.guzzing.studayserver.domain.auth.dto.AuthResponse;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientStrategy clientStrategy;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberJpaRepository;
    private final RefreshTokenService refreshTokenService;

    public ClientService(ClientStrategy clientStrategy, AuthTokenProvider authTokenProvider,
            MemberRepository memberJpaRepository, RefreshTokenService refreshTokenService) {
        this.clientStrategy = clientStrategy;
        this.authTokenProvider = authTokenProvider;
        this.memberJpaRepository = memberJpaRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public AuthResponse login(String client, String accessToken) {
        ClientProxy clientProxy = clientStrategy.getClient(client);

        Member clientMember = clientProxy.getUserData(accessToken);
        String socialId = clientMember.getSocialId();

        Optional<Member> memberOptional = memberJpaRepository.findMemberIfExisted(socialId);
        Member savedMember = memberOptional.orElseGet(() -> memberJpaRepository.save(clientMember));

        AuthToken newAuthToken = refreshTokenService.saveAccessTokenCache(savedMember.getId(), socialId);

        return AuthResponse.builder()
                .appToken(newAuthToken.getToken())
                .isNewMember(!memberOptional.isPresent())
                .userId(savedMember.getId())
                .name(savedMember.getNickName())
                .build();
    }

}
