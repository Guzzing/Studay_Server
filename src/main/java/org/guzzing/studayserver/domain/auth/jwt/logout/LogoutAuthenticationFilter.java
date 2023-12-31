package org.guzzing.studayserver.domain.auth.jwt.logout;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.guzzing.studayserver.domain.auth.jwt.JwtHeaderUtil;
import org.guzzing.studayserver.domain.auth.service.AuthService;
import org.springframework.web.filter.OncePerRequestFilter;

public class LogoutAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthService authService;

    public LogoutAuthenticationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String AUTHORIZATION_HEADER = request.getHeader(HEADER_AUTHORIZATION);

        if (AUTHORIZATION_HEADER != null && AUTHORIZATION_HEADER.startsWith(TOKEN_PREFIX)) {
            String tokenStr = JwtHeaderUtil.getAccessToken(request);
            authService.isLogout(tokenStr);

            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
