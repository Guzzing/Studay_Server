package org.guzzing.studayserver.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
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

    @Test
    void register_success() {
        // Given
        String memberNickname = "nickname";
        String memberEmail = "test@email.com";

        Member member = Member.of(null, "123", MemberProvider.KAKAO, RoleType.USER);
        Member savedMember = memberRepository.save(member);

        MemberRegisterParam param = new MemberRegisterParam(savedMember.getId(), memberNickname, memberEmail, List.of(
                new MemberRegisterParam.MemberRegisterChildInfoParam("childNickname1", "grade1"),
                new MemberRegisterParam.MemberRegisterChildInfoParam("childNickname2", "grade2")
        ));

        // When
        memberService.register(param);

        // Then
        assertThat(member.getChildren()).size().isEqualTo(2);
    }
}
