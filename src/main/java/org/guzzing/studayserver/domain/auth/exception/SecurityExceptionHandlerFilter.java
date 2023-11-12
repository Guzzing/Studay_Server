package org.guzzing.studayserver.domain.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String CONTENT_TYPE = "application/json";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e ) {
            setErrorResponse(e.getErrorCode(), response);
        } catch (TokenValidFailedException e) {
            setErrorResponse(e.getErrorCode(),response);
        } catch (TokenIsLogoutException e) {
            setErrorResponse(e.getErrorCode(), response);
        }
    }

    public void setErrorResponse(ErrorCode errorCode,
                                 HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(errorResponse);

        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(json);
    }

}
