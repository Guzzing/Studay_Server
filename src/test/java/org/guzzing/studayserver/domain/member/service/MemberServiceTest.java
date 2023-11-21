package org.guzzing.studayserver.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam.MemberAdditionalChildParam;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @DisplayName("멤버 등록 성공")
    @Test
    void register_success() {
        // Given
        String memberNickname = "nickname";
        String memberEmail = "test@email.com";
        Long memberId = 1L;

        Member member = Member.of(null, "123", MemberProvider.KAKAO, RoleType.USER);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        MemberRegisterParam param = new MemberRegisterParam(memberId, memberNickname, memberEmail, List.of(
                new MemberAdditionalChildParam("childNickname1", "초등학교 1학년"),
                new MemberAdditionalChildParam("childNickname2", "초등학교 3학년")
        ));

        // When
        memberService.register(param);

        // Then
        assertThat(member.getEmail()).isEqualTo(memberEmail);
        assertThat(member.getChildren()).hasSize(2);
    }

    @Test
    @DisplayName("멤버 상세정보 조회")
    void getById_success() {
        // Given
        Long memberId = 1L;
        Member member = Member.of(new NickName("TestUser"), "123", MemberProvider.KAKAO, RoleType.USER);

        given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

        // When
        MemberInformationResult result = memberService.getById(memberId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.nickname()).isEqualTo("TestUser");
        assertThat(result.email()).isEmpty();

        assertThat(result.childResults()).isNotNull();
        assertThat(result.childResults()).isEmpty();
    }
}
