package org.guzzing.studayserver.domain.child.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ChildRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChildRepository childRepository;

    @DisplayName("멤버에게 할당된 아이를 찾는다.")
    @Test
    void findByIdAndMemberId() {
        // given
        Member member = Member.of(new NickName("멤버 아이디"), "123", MemberProvider.KAKAO, RoleType.USER);
        Member savedMember = memberRepository.save(member);

        Child child = new Child("아이 닉네임", "초등학교 1학년", "imageUrl");
        child.assignToNewMemberOnly(savedMember);
        Child savedChild = childRepository.save(child);

        // when
        Optional<Child> foundOptionalChild = childRepository.findByIdAndMemberId(child.getId(), savedChild.getId());

        // then
        assertThat(foundOptionalChild).isPresent();
        assertThat(foundOptionalChild.get()).hasFieldOrPropertyWithValue("id", savedChild.getId());
        assertThat(foundOptionalChild.get().getMember()).hasFieldOrPropertyWithValue("id", savedMember.getId());
    }
}
