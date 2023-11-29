package org.guzzing.studayserver.domain.child.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.assertj.core.api.Assertions;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ChildTest {


    @Nested
    class AssignToNewMemberOnly {

        private Child child;
        private Member member;

        @BeforeEach
        void setUp() {
            child = new Child("아이 닉네임", "초등학교 1학년", "imageUrl");
            member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);
        }

        @DisplayName("멤버가 없는 아이에게 새 멤버를 할당한다.")
        @Test
        void whenChildHasNoMember_assignsMember() {
            // When & Then
            assertThatCode(() -> child.assignToNewMemberOnly(member))
                    .doesNotThrowAnyException();
        }

        @DisplayName("이미 멤버가 할당된 아이에게 새 멤버를 할당하려고 하면 예외를 던진다.")
        @Test
        void givenAssignToNewMemberOnly_WhenChildAlreadyHasMember_ThrowsException() {
            // Given
            child.assignToNewMemberOnly(member);

            // 실행 & 검증
            Assertions.assertThatThrownBy(() -> child.assignToNewMemberOnly(member))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("이미 멤버가 할당되어 있습니다.");
        }
    }

    @DisplayName("아이의 정보를 수정한다.")
    @Test
    void update_success() {
        // Given
        Child child = new Child("아이 닉네임", "초등학교 1학년", "imageUrl");

        String updatedNickname = "닉네임 수정";
        String updatedGrade = "초등학교 2학년";

        // When
        child.update(updatedNickname, updatedGrade);

        // Then
        Assertions.assertThat(child.getNickName()).isEqualTo(updatedNickname);
        Assertions.assertThat(child.getGrade()).isEqualTo(updatedGrade);
    }
}
