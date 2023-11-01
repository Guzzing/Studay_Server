package org.guzzing.studayserver.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles({"dev", "oauth"})
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;


    @Test
    void register_success() {
        // Given
        Long memberId = 1L;
        String memberNickname = "nickname";
        String memberEmail = "test@email.com";

        MemberRegisterParam param = new MemberRegisterParam(memberId, memberNickname, memberEmail, List.of(
                new MemberRegisterParam.MemberRegisterChildInfoParam("childNickname1", "grade1"),
                new MemberRegisterParam.MemberRegisterChildInfoParam("childNickname2", "grade2")
        ));

        Member member = Member.of(null, "123", MemberProvider.KAKAO, RoleType.USER);
        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // When
        memberService.register(param);

        // Then
        assertThat(member.getChildren()).size().isEqualTo(2);
    }
}
