package org.guzzing.studayserver.domain.like.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.guzzing.studayserver.testutil.fixture.TestConfig.AUTHORIZATION_HEADER;
import static org.guzzing.studayserver.testutil.fixture.TestConfig.BEARER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.service.LikeService;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.member.annotation.ValidMemberAspect;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.guzzing.studayserver.testutil.fixture.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class LikeRestControllerTest {

    private static final String TAG = "좋아요 API";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestConfig testConfig;

    @Autowired
    private LikeService likeService;

    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private MemberAccessService memberAccessService;
    @MockBean
    private ValidMemberAspect validMemberAspect;

    private final Long academyId = 1L;
    private LikePostParam param;

    @BeforeEach
    void setUp() {
        Long memberId = 1L;
        LikePostRequest request = new LikePostRequest(academyId);
        param = LikePostRequest.to(request, memberId);
    }

    @Test
    @DisplayName("헤더에 JWT 로 들어오는 멤버 아이디와 바디로 전달되는 학원 아이디를 이용해서 좋아요를 등록한다.")
    @WithMockCustomOAuth2LoginUser
    void createLike_MemberIdAndAcademyId_RegisterLike() throws Exception {
        // Given
        LikePostRequest request = new LikePostRequest(academyId);
        String jsonBody = objectMapper.writeValueAsString(request);

        // When
        ResultActions perform = mockMvc.perform(post("/likes")
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.likeId").isNumber())
                .andExpect(jsonPath("$.memberId").isNumber())
                .andExpect(jsonPath("$.academyId").value(1L))
                .andDo(document("post-like",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("좋아요 등록")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                                )
                                .requestFields(
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디")
                                )
                                .responseFields(
                                        fieldWithPath("likeId").type(NUMBER).description("좋아요 아이디"),
                                        fieldWithPath("memberId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("등록한 좋아요를 제거한다.")
    @WithMockCustomOAuth2LoginUser
    void removeLike_LikeId_Remove() throws Exception {
        // Given
        LikePostResult postResult = likeService.createLikeOfAcademy(param);

        // When
        ResultActions perform = mockMvc.perform(delete("/likes/{likeId}", postResult.likeId())
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt()));

        // Then
        perform.andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-like",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("좋아요 제거")
                                .pathParameters(
                                        parameterWithName("likeId").description("좋아요 아이디")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("학원 아이디로 좋아요를 삭제한다.")
    @WithMockCustomOAuth2LoginUser
    void removeLikeOfAcademy_AcademyId_Delete() throws Exception {
        // Given
        LikePostResult postResult = likeService.createLikeOfAcademy(param);

        // When
        ResultActions perform = mockMvc.perform(delete("/likes")
                .header(AUTHORIZATION_HEADER, BEARER)
                .queryParam("academyId", String.valueOf(postResult.academyId())));

        // Then
        perform.andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-like-academyId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION_HEADER).description("JWT 토큰 (Bearer)")
//                        ),
//                        queryParameters(
//                                parameterWithName("academyId").description("학원 아이디")
//                        )
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("학원 아이디로 좋아요 제거")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                                )
                                .queryParameters(
                                        parameterWithName("academyId").description("학원 아이디")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("좋아요한 학원 비용 정보를 응답받는다.")
    @WithMockCustomOAuth2LoginUser
    void getAllLikes_MemberId() throws Exception {
        // Given
        given(academyAccessService.findAcademyFeeInfo(any()))
                .willReturn(new AcademyFeeInfo("학원명", 100L));

        likeService.createLikeOfAcademy(param);

        // When
        ResultActions perform = mockMvc.perform(get("/likes")
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt())
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.likeAcademyInfos").isArray())
                .andExpect(jsonPath("$.totalFee").isNumber())
                .andDo(document("get-like",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("나의 좋아요 조회")
                                .responseFields(
                                        fieldWithPath("likeAcademyInfos").type(ARRAY).description("좋아요한 학원 비용 목록"),
                                        fieldWithPath("likeAcademyInfos[].likeId").type(NUMBER).description("좋아요 아이디"),
                                        fieldWithPath("likeAcademyInfos[].academyId").type(NUMBER)
                                                .description("좋아요한 학원 아이디"),
                                        fieldWithPath("likeAcademyInfos[].academyName").type(STRING).description("학원명"),
                                        fieldWithPath("likeAcademyInfos[].expectedFee").description("예상 교육비"),
                                        fieldWithPath("totalFee").type(NUMBER).description("총 비용")
                                )
                                .build()
                        )
                ));
    }

}
