package org.guzzing.studayserver.domain.child.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.model.NickName;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"dev", "oauth"})
class ChildServiceTest {

    @Autowired
    private ChildService childService;

    @MockBean
    private ChildRepository childRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Nested
    class Create {

        @DisplayName("아이 생성 성공")
        @Test
        void success() {
            // Given
            Long memberId = 1L;
            ChildCreateParam param = new ChildCreateParam("아이 닉네임", "초등학교 1학년", memberId);
            Long expectedChildId = 2L;

            Member mockMember = mock(Member.class);
            Child mockChild = mock(Child.class);

            given(mockChild.getId()).willReturn(expectedChildId);
            given(memberRepository.findById(memberId)).willReturn(Optional.ofNullable(mockMember));
            given(childRepository.save(any(Child.class))).willReturn(mockChild);

            // When
            Long savedChildId = childService.create(param);

            // Then
            assertThat(savedChildId).isEqualTo(expectedChildId);
            verify(childRepository).save(any(Child.class));
        }

        @DisplayName("잘못된 멤버 아이디로 인한 예외를 발생시킨다.")
        @Test
        void givenInvalidMemberId_throwException() {
            // Given
            Long invalidMemberId = 999L;
            ChildCreateParam param = new ChildCreateParam("아이 닉네임", "초등학교 1학년", invalidMemberId);

            given(memberRepository.findById(invalidMemberId)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> childService.create(param))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("잘못된 멤버 아이디입니다: " + invalidMemberId);
        }

        @DisplayName("멤버에게 할당된 아이의 수가 최대치를 넘을 경우 예외를 발생시킨다")
        @Test
        void whenExceedingMaxChildren_throwException() {
            // Given
            Long memberId = 1L;
            ChildCreateParam param = new ChildCreateParam("아이 닉네임", "초등학교 1학년", memberId);

            Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);
            for (int i = 0; i < Member.CHILDREN_MAX_SIZE; i++) {
                member.addChild(mock(Child.class));
            }
            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            // When & Then
            assertThatThrownBy(() -> childService.create(param))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.format("멤버당 아이는 최대 %d까지만 등록할 수 있습니다.", Member.CHILDREN_MAX_SIZE));
        }

    }
}
