package org.guzzing.studayserver.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("존재하는 멤버의 정보 변경하고 아이를 등록한다.")
    @Test
    void register_success() {
        // given
        Member savedMember = memberRepository.save(createMember());
        Long memberId = savedMember.getId();

        MemberRegisterParam param = new MemberRegisterParam(
                savedMember.getNickName(),
                "test@test.org",
                List.of(
                        new ChildCreateParam("childNickname1", "초등학교 1학년"),
                        new ChildCreateParam("childNickname2", "초등학교 3학년")
                ));

        // when
        memberService.register(param, memberId);

        // then
        assertThat(savedMember.getChildren()).hasSize(2);
    }

    @Test
    @DisplayName("멤버가 존재하면 멤버의 정보를 알려준다.")
    void getById_success() {
        // given
        Member savedMember = memberRepository.save(createMember());
        Long memberId = savedMember.getId();

        // when
        MemberInformationResult result = memberService.getById(memberId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.nickname()).isEqualTo("TestUser");
        assertThat(result.email()).isEmpty();
        assertThat(result.childResults()).isEmpty();
    }

    private Member createMember() {
        return Member.of(new NickName("TestUser"), "123", MemberProvider.KAKAO, RoleType.USER);
    }
}
