package org.guzzing.studayserver.domain.auth.config;

import java.util.List;
import java.util.stream.Stream;

import org.guzzing.studayserver.domain.auth.exception.SecurityExceptionHandlerFilter;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.jwt.JwtAuthenticationFilter;
import org.guzzing.studayserver.domain.auth.jwt.logout.LogoutAuthenticationFilter;
import org.guzzing.studayserver.domain.auth.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthTokenProvider authTokenProvider;
    private final AuthService authService;
    private final SecurityExceptionHandlerFilter securityExceptionHandlerFilter;

    public SecurityConfig(AuthTokenProvider authTokenProvider, AuthService authService, SecurityExceptionHandlerFilter securityExceptionHandlerFilter) {
        this.authTokenProvider = authTokenProvider;
        this.authService = authService;
        this.securityExceptionHandlerFilter = securityExceptionHandlerFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtTokenValidationFilter = new JwtAuthenticationFilter(authTokenProvider);
        LogoutAuthenticationFilter logoutAuthenticationFilter = new LogoutAuthenticationFilter(authTokenProvider, authService);

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                Stream
                                        .of(Constants.permitAllArray)
                                        .map(AntPathRequestMatcher::antMatcher)
                                        .toArray(AntPathRequestMatcher[]::new)
                        )
                        .permitAll()
                        .anyRequest().authenticated())
                .cors(cors -> corsConfigurationSource())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable())
                .addFilterBefore(jwtTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(logoutAuthenticationFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(securityExceptionHandlerFilter, LogoutAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
