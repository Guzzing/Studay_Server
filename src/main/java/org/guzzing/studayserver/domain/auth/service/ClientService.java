package org.guzzing.studayserver.domain.auth.service;

import java.util.Optional;
import org.guzzing.studayserver.domain.auth.client.ClientStrategy;
import org.guzzing.studayserver.domain.auth.client.ClientStrategyHandler;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLoginResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientStrategyHandler clientStrategyHandler;
    private final MemberRepository memberJpaRepository;
    private final AuthService authService;

    public ClientService(
            ClientStrategyHandler clientStrategyHandler,
            MemberRepository memberJpaRepository,
            AuthService authService
    ) {
        this.clientStrategyHandler = clientStrategyHandler;
        this.memberJpaRepository = memberJpaRepository;
        this.authService = authService;
    }

    @Transactional
    public AuthLoginResult login(String client, String accessToken) {
        ClientStrategy clientStrategy = clientStrategyHandler.getClientStrategy(client);

        Member clientMember = clientStrategy.getUserData(accessToken);
        String socialId = clientMember.getSocialId();

        Optional<Member> memberOptional = memberJpaRepository.findMemberIfExisted(socialId);
        Member savedMember = memberOptional.orElseGet(() -> memberJpaRepository.save(clientMember));

        AuthToken newAuthToken = authService.saveAccessToken(savedMember.getId(), socialId);

        return AuthLoginResult.of(
                newAuthToken.getToken(),
                memberOptional.isEmpty(),
                savedMember.getId(),
                savedMember.getNickName()
        );
    }

}
