package org.guzzing.studayserver.domain.member.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        Child child1 = new Child("child1", "중학교 1학년");
        child1.assignToNewMemberOnly(member);

        Child child2 = new Child("child2", "중학교 2학년");
        child2.assignToNewMemberOnly(member);

        // Then
        Assertions.assertThat(member.getChildren()).size().isEqualTo(2);
    }

    @DisplayName("아이 추가시 제한된 아이 숫자를 넘긴다면 예외를 발생시킨다.")
    @Test
    void addChild_failure_exceedsLimit() {
        // Given
        for (int i = 0; i < 5; i++) {
            Child child = new Child("child1", "중학교 1학년");
            child.assignToNewMemberOnly(member);
        }

        Child exceededChild = new Child("child1", "중학교 1학년");

        // When & Then
        assertThatThrownBy(() -> exceededChild.assignToNewMemberOnly(member))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("멤버당 아이는 최대 %d까지만 등록할 수 있습니다.", Member.CHILDREN_MAX_SIZE));
    }
}
