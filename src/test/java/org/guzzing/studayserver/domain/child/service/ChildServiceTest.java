package org.guzzing.studayserver.domain.child.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.param.ChildDeleteParam;
import org.guzzing.studayserver.domain.child.service.param.ChildModifyParam;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
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
            Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);
            for (int i = 0; i < Member.CHILDREN_MAX_SIZE; i++) {
                member.addChild(mock(Child.class));
            }

            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            ChildCreateParam param = new ChildCreateParam("아이 닉네임", "초등학교 1학년", memberId);

            given(childRepository.save(any())).willReturn(new Child(param.nickname(), param.grade()));

            // When & Then
            assertThatThrownBy(() -> childService.create(param))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.format("멤버당 아이는 최대 %d까지만 등록할 수 있습니다.", Member.CHILDREN_MAX_SIZE));
        }

    }

    @Nested
    class FindByMemberId {

        @DisplayName("멤버의 아이들의 정보를 반환한다.")
        @Test
        void success() {
            // Given
            Long memberId = 1L;

            Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);

            Child child1 = new Child("아이 닉네임1", "초등학교 1학년");
            Child child2 = new Child("아이 닉네임2", "초등학교 2학년");

            Child spyChild1 = spy(child1);
            spyChild1.assignToNewMemberOnly(member);
            Child spyChild2 = spy(child2);
            spyChild2.assignToNewMemberOnly(member);

            Long spyChild1Id = 100L;
            Long spyChild2Id = 200L;
            given(spyChild1.getId()).willReturn(spyChild1Id);
            given(spyChild2.getId()).willReturn(spyChild2Id);

            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            ChildrenFindResult expectedResult = new ChildrenFindResult(List.of(
                    new ChildFindResult(spyChild1Id, spyChild1.getNickName(), spyChild1.getGrade(), "휴식 중!"),
                    new ChildFindResult(spyChild2Id, spyChild2.getNickName(), spyChild2.getGrade(), "휴식 중!")
            ));

            // When
            ChildrenFindResult actualResult = childService.findByMemberId(memberId);

            // Then
            assertThat(actualResult).isEqualTo(expectedResult);
        }

        @DisplayName("잘못된 멤버 아이디로 인한 예외를 발생시킨다.")
        @Test
        void givenInvalidMemberId_throwException() {
            // Given
            Long invalidMemberId = 999L;

            given(memberRepository.findById(invalidMemberId)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> childService.findByMemberId(invalidMemberId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("잘못된 멤버 아이디입니다: " + invalidMemberId);
        }

    }

    @Nested
    class Delete {

        @DisplayName("할당된 아이를 삭제한다.")
        @Test
        void deleteChild() {
            // Given
            Long memberId = 1L;
            Long childId = 20L;

            Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);

            Child child = new Child("아이 닉네임", "초등학교 1학년");

            Child spyChild = spy(child);
            spyChild.assignToNewMemberOnly(member);
            given(spyChild.getId()).willReturn(childId);

            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            ChildDeleteParam param = new ChildDeleteParam(memberId, childId);

            // When
            childService.delete(param);

            // Then
            assertThat(member.getChildren()).isEmpty();
        }

        @DisplayName("멤버에 할당되지 않은 아이일 경우 삭제하지 않는다.")
        @Test
        void givenChildNotAssignedToMember_doNothing() {
            Long memberId = 1L;
            Long childId = 20L;

            Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);
            Member differentMember = Member.of(new NickName("다른 멤버 닉네임"), "456", MemberProvider.KAKAO, RoleType.USER);

            Child child = new Child("아이 닉네임", "초등학교 1학년");
            Child spyChild = spy(child);
            spyChild.assignToNewMemberOnly(differentMember);
            given(spyChild.getId()).willReturn(childId);

            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));
            given(childRepository.findById(childId)).willReturn(Optional.of(spyChild));

            ChildDeleteParam param = new ChildDeleteParam(memberId, childId);

            // When
            childService.delete(param);

            // Then
            assertThat(differentMember.getChildren()).containsExactly(spyChild);
        }

        @DisplayName("잘못된 멤버 아이디인 경우 예외를 발생시킨다.")
        @Test
        void givenInvalidMemberId_throwsException() {
            // Given
            Long invalidMemberId = 999L;

            given(memberRepository.findById(invalidMemberId)).willReturn(Optional.empty());

            ChildDeleteParam param = new ChildDeleteParam(invalidMemberId, 1L);

            // When & Then
            Assertions.assertThatThrownBy(() -> childService.delete(param))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("잘못된 멤버 아이디입니다");
        }
    }

    @Nested
    class Modify {

        @DisplayName("원하는 아이의 정보를 수정한다.")
        @Test
        void success() {
            // Given
            ChildModifyParam param = new ChildModifyParam("수정할 아이 닉네임", "초등학교 2학년", 1L, 10L);

            Child mockChild = mock(Child.class);
            given(mockChild.getId()).willReturn(param.childId());

            Member mockMember = mock(Member.class);
            given(mockMember.findChild(param.childId())).willReturn(Optional.of(mockChild));

            given(memberRepository.findById(param.memberId())).willReturn(Optional.of(mockMember));

            // When
            Long updatedChildId = childService.modify(param);

            // Then
            assertThat(updatedChildId).isEqualTo(param.childId());
            verify(mockChild).update(param.nickname(), param.grade());
        }

        @DisplayName("존재하지 않는 멤버라면 예외를 발생시킨다.")
        @Test
        void whenNonExistentMember_throwException() {
            // Given
            ChildModifyParam param = new ChildModifyParam("수정할 아이 닉네임", "초등학교 2학년", 1L, 10L);

            given(memberRepository.findById(param.memberId())).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> childService.modify(param))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("잘못된 멤버 아이디입니다");
        }

        @DisplayName("멤버에 존재하지 않는 아이라면 예외를 발생시킨다.")
        @Test
        void whenUnassignedChildId_throwsException() {
            // Given
            ChildModifyParam param = new ChildModifyParam("수정할 아이 닉네임", "초등학교 2학년", 1L, 10L);

            Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);

            given(memberRepository.findById(param.memberId())).willReturn(Optional.of(member));

            // When & Then
            assertThatThrownBy(() -> childService.modify(param))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("찾을 수 없는 아이입니다");
        }
    }
}
