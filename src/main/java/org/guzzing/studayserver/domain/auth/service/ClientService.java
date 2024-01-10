package org.guzzing.studayserver.domain.auth.service;

import java.util.Optional;
import org.guzzing.studayserver.domain.auth.client.ClientProxy;
import org.guzzing.studayserver.domain.auth.client.ClientStrategy;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLoginResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientStrategy clientStrategy;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    public ClientService(
            ClientStrategy clientStrategy,
            MemberRepository memberJpaRepository,
            AuthService authService
    ) {
        this.clientStrategy = clientStrategy;
        this.memberRepository = memberJpaRepository;
        this.authService = authService;
    }

    @Transactional
    public AuthLoginResult login(String client, String accessToken) {
        ClientProxy clientProxy = clientStrategy.getClient(client);

        Member clientMember = clientProxy.getUserData(accessToken);
        String socialId = clientMember.getSocialId();

        Optional<Member> memberOptional = memberRepository.findMemberIfExisted(socialId);
        Member savedMember = memberOptional.orElseGet(() -> memberRepository.save(clientMember));

        AuthToken newAuthToken = authService.saveAccessTokenCache(savedMember.getId(), socialId);

        return AuthLoginResult.of(
                newAuthToken.getToken(),
                memberOptional.isEmpty(),
                savedMember.getId(),
                savedMember.getNickName()
        );
    }

}
