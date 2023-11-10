package org.guzzing.studayserver.domain.auth.exception;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.guzzing.studayserver.global.error.response.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e ) {
            setErrorResponse(e.getErrorCode(),request, response);
        } catch (TokenValidFailedException e) {
            setErrorResponse(e.getErrorCode(),request, response);
        } catch (TokenIsLogoutException e) {
            setErrorResponse(e.getErrorCode(),request, response);
        }

    }

    public void setErrorResponse(ErrorCode errorCode,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(ErrorResponse.of(errorCode).convertToJson());
    }

}
