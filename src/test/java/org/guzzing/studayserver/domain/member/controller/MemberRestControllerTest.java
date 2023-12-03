package org.guzzing.studayserver.domain.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Stream;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.guzzing.studayserver.domain.member.controller.request.MemberRegisterRequest;
import org.guzzing.studayserver.domain.member.service.MemberFacade;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberFacade memberFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class Register {

        @DisplayName("멤버 등록시 정상 값이면 OK를 반환한다.")
        @Test
        @WithMockCustomOAuth2LoginUser(memberId = 1L)
        void statusIsOk() throws Exception {
            // Given
            MemberRegisterRequest request = new MemberRegisterRequest("nickname", "email@example.com", List.of(
                    new ChildCreateRequest("Child1", "중학교 1학년")));

            // When
            when(memberService.register(any(MemberRegisterParam.class), any(Long.class))).thenReturn(1L);

            // Then
            mockMvc.perform(patch("/members")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().is(HttpStatus.OK.value()));
        }

        @DisplayName("잘못된 Request는 400에러를 반환한다.")
        @ParameterizedTest
        @WithMockCustomOAuth2LoginUser
        @MethodSource("provideInvalidRequests")
        void statusIsBadRequest(MemberRegisterRequest invalidRequest) throws Exception {
            // Then
            mockMvc.perform(patch("/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }

        private static Stream<Arguments> provideInvalidRequests() {
            return Stream.of(
                    // 빈 닉네임
                    Arguments.of(new MemberRegisterRequest("", "email@example.com", List.of(
                            new ChildCreateRequest("Child1", "Grade1")
                    ))),

                    // 잘못된 이메일
                    Arguments.of(new MemberRegisterRequest("nickname", "invalid_email_format", List.of(
                            new ChildCreateRequest("Child1", "Grade1")
                    ))),

                    // 5명 이상의 아이
                    Arguments.of(new MemberRegisterRequest("nickname", "email@example.com", List.of(
                            new ChildCreateRequest("Child1", "Grade1"),
                            new ChildCreateRequest("Child2", "Grade2"),
                            new ChildCreateRequest("Child3", "Grade3"),
                            new ChildCreateRequest("Child4", "Grade4"),
                            new ChildCreateRequest("Child5", "Grade5"),
                            new ChildCreateRequest("Child6", "Grade6")
                    ))),

                    // 빈 아이 닉네임
                    Arguments.of(new MemberRegisterRequest("nickname", "email@example.com", List.of(
                            new ChildCreateRequest("", "Grade1")
                    ))),

                    // 빈 학년
                    Arguments.of(new MemberRegisterRequest("nickname", "email@example.com", List.of(
                            new ChildCreateRequest("Child1", "")
                    )))
            );
        }
    }

    @Nested
    class getInformation {

        @Test
        @WithMockCustomOAuth2LoginUser(memberId = 999L)
        @DisplayName("멤버가 존재하지 않는다면 예외를 발생시킨다.")
        void whenMemberIdDoesNotExist_shouldThrowException() throws Exception {
            // Given
            Long nonExistentMemberId = 999L;
            given(memberService.getById(nonExistentMemberId)).willThrow(new IllegalArgumentException());

            // When & Then
            mockMvc.perform(get("/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .param("memberId", nonExistentMemberId.toString()))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithMockCustomOAuth2LoginUser(memberId = 1L)
        @DisplayName("멤버가 존재한다면 상세 정보를 반환한다.")
        void whenMemberIdExists_shouldReturnMemberInformation() throws Exception {
            // Given
            String memberNickname = "테스트 닉네임";
            String memberEmail = "test@example.com";
            Long existingMemberId = 1L;
            String file = "test.png";
            List<MemberChildInformationResult> list = List.of(new MemberChildInformationResult(1L, "박명수", file, null));
            MemberInformationResult mockResult = new MemberInformationResult(memberNickname, memberEmail, list);
            given(memberService.getById(existingMemberId)).willReturn(mockResult);

            // When & Then
            mockMvc.perform(get("/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .param("memberId", existingMemberId.toString()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nickname").value(memberNickname))
                    .andExpect(jsonPath("$.email").value(memberEmail))
                    .andExpect(jsonPath("$.childInformationResponses").isNotEmpty())
                    .andExpect(jsonPath("$.childInformationResponses[0]").exists())
                    .andExpect(jsonPath("$.childInformationResponses[0].childProfileImageUrl").value(file));
        }
    }
}
