package org.guzzing.studayserver.domain.member.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberTest {

    private Member member;

    @BeforeEach
    public void setUp() {
        member = Member.of(new NickName("testNick"), "12345", MemberProvider.KAKAO, RoleType.USER);
    }

    @DisplayName("업데이트 성공")
    @Test
    void update_success() {
        // Given
        String newNickName = "updatedNick";
        String newEmail = "updated@test.com";

        // When
        member.update(newNickName, newEmail);

        // Then
        assertThat(member.getNickName()).isEqualTo(newNickName);
        assertThat(member.getEmail()).isEqualTo(newEmail);
    }

    @DisplayName("아이 추가 성공")
    @Test
    void addChild_success() {
        // Given & When
        Child child1 = new Child("child1", "중학교 1학년", "imageUrl");
        child1.assignToNewMemberOnly(member);

        Child child2 = new Child("child2", "중학교 2학년", "imageUrl");
        child2.assignToNewMemberOnly(member);

        // Then
        Assertions.assertThat(member.getChildren()).size().isEqualTo(2);
    }

    @DisplayName("아이 추가시 제한된 아이 숫자를 넘긴다면 예외를 발생시킨다.")
    @Test
    void addChild_failure_exceedsLimit() {
        // Given
        for (int i = 0; i < 5; i++) {
            Child child = new Child("child1", "중학교 1학년", "imageUrl");
            child.assignToNewMemberOnly(member);
        }

        Child exceededChild = new Child("child1", "중학교 1학년", "imageUrl");

        // When & Then
        assertThatThrownBy(() -> exceededChild.assignToNewMemberOnly(member))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("멤버당 아이는 최대 %d까지 등록할 수 있습니다.", Member.CHILDREN_MAX_SIZE));
    }

    @Nested
    class FindChild {

        @DisplayName("멤버에 아이가 존재한다면 반환한다.")
        @Test
        void success() {
            // Given
            Member member = new Member(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);

            Long childId = 1L;
            Child child = new Child("아이 닉네임", "초등학교 1학년", "imageUrl");

            Child spyChild = spy(child);
            spyChild.assignToNewMemberOnly(member);
            given(spyChild.getId()).willReturn(childId);

            // When
            Optional<Child> actualChild = member.findChild(childId);

            // Then
            assertThat(actualChild).contains(spyChild);
        }

        @DisplayName("멤버에 아이가 존재하지 않는다면 empty를 반환한다.")
        @Test
        void whenNonExistentChildId_returnedEmpty() {
            // Given
            Member member = new Member(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);

            Long nonExistentChildId = 1L;

            // When
            Optional<Child> actualOptionalChild = member.findChild(nonExistentChildId);

            // Then
            assertThat(actualOptionalChild).isEmpty();
        }
    }
}
