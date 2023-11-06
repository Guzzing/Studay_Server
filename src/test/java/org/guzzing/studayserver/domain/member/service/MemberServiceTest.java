package org.guzzing.studayserver.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.guzzing.studayserver.domain.child.model.NickName;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam.MemberAdditionalChildParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"dev", "oauth"})
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버 등록 성공")
    @Test
    void register_success() {
        // Given
        String memberNickname = "nickname";
        String memberEmail = "test@email.com";

        Member member = Member.of(new NickName("멤버 닉네임"), "123", MemberProvider.KAKAO, RoleType.USER);
        Member savedMember = memberRepository.save(member);

        MemberRegisterParam param = new MemberRegisterParam(savedMember.getId(), memberNickname, memberEmail, List.of(
                new MemberAdditionalChildParam("childNickname1", "초등학교 1학년"),
                new MemberAdditionalChildParam("childNickname2", "초등학교 3학년")
        ));

        // When
        memberService.register(param);

        // Then
        assertThat(member.getChildren()).size().isEqualTo(2);
    }
}
