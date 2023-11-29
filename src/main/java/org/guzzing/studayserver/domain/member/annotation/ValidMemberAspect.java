package org.guzzing.studayserver.domain.member.annotation;

import java.lang.reflect.Parameter;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidMemberAspect {

    private final MemberRepository memberRepository;

    public ValidMemberAspect(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Before("@annotation(ValidMember)")
    public void validateMember(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Parameter[] methodParameters = methodSignature.getMethod().getParameters();

        OptionalInt validatedMemberIdIndex = IntStream.range(0, methodParameters.length)
                .filter(i -> methodParameters[i].isAnnotationPresent(ValidatedMemberId.class))
                .findFirst();

        validatedMemberIdIndex.ifPresent(index -> validateMemberId(joinPoint, index));
    }

    private void validateMemberId(JoinPoint joinPoint, int parameterIndex) {
        Object memberIdArgument = joinPoint.getArgs()[parameterIndex];
        if (memberIdArgument instanceof Long memberId) {
            memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));
        }
    }

}
