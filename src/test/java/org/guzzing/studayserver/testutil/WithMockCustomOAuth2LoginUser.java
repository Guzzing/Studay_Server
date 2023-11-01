package org.guzzing.studayserver.testutil;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomOAuth2LoginUserSecurityContextFactory.class)
public @interface WithMockCustomOAuth2LoginUser {

    long memberId() default 1L;

    String socialId() default "a";

    String username() default "username";

}
