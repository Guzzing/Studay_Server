package org.guzzing.studayserver.testutil.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.auth.jwt.CustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomOAuth2LoginUserSecurityContextFactory implements
        WithSecurityContextFactory<WithMockCustomOAuth2LoginUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomOAuth2LoginUser oAuth2LoginUser) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("memberId", oAuth2LoginUser.memberId());
        attributes.put("socialId", oAuth2LoginUser.socialId());
        attributes.put("username", oAuth2LoginUser.username());

        final CustomUser customUser = CustomUser.builder()
                .memberId(oAuth2LoginUser.memberId())
                .socialId(oAuth2LoginUser.socialId())
                .authorities(null)
                .build();

        final Authentication token = new UsernamePasswordAuthenticationToken(customUser, "dd",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        context.setAuthentication(token);
        return context;

    }
}
