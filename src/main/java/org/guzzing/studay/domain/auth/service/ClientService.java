package org.guzzing.studay.domain.auth.service;

import org.guzzing.studay.domain.auth.client.ClientProxy;
import org.guzzing.studay.domain.auth.client.ClientStrategy;
import org.guzzing.studay.domain.auth.dto.AuthResponse;
import org.guzzing.studay.domain.auth.jwt.AuthToken;
import org.guzzing.studay.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studay.domain.member.model.Member;
import org.guzzing.studay.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientStrategy clientStrategy;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberJpaRepository;
    private final RefreshTokenService refreshTokenService;

    public ClientService(ClientStrategy clientStrategy, AuthTokenProvider authTokenProvider, MemberRepository memberJpaRepository, RefreshTokenService refreshTokenService) {
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
        
        AuthToken newAuthToken = refreshTokenService.saveAccessTokenCache(savedMember.getId(),socialId);

        return AuthResponse.builder()
                .appToken(newAuthToken.getToken())
                .isNewMember(!memberOptional.isPresent())
                .userId(savedMember.getId())
                .name(savedMember.getNickName())
                .build();
    }

}
