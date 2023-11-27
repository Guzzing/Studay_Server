package org.guzzing.studayserver.domain.member.annotation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidMemberAspectTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ValidMemberAspect validMemberAspect;

    @Test
    void validated() throws NoSuchMethodException {
        // given
        JoinPoint joinPoint = mock(JoinPoint.class);
        MethodSignature signature = mock(MethodSignature.class);
        Method method = SampleClass.class.getMethod("sampleMethod", Long.class);
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getMethod()).willReturn(method);

        Long invalidMemberId = 123L;
        given(joinPoint.getArgs()).willReturn(new Object[]{invalidMemberId});
        given(memberRepository.findById(invalidMemberId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> validMemberAspect.validateMember(joinPoint))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하지 않는 아이디입니다.");
    }

    private static class SampleClass {

        @ValidMember
        public void sampleMethod(@ValidatedMemberId Long memberId) {
        }
    }
}
