package org.guzzing.studayserver.domain.review.controller;

import static org.guzzing.studayserver.testutil.fixture.TestConfig.AUTHORIZATION_HEADER;
import static org.guzzing.studayserver.testutil.fixture.TestConfig.BEARER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.domain.review.controller.dto.request.ReviewPostRequest;
import org.guzzing.studayserver.domain.review.fixture.ReviewFixture;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
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

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestConfig testConfig;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberAccessService memberAccessService;
    @MockBean
    private AcademyAccessService academyAccessService;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    @DisplayName("리뷰 타입이 3개 이하고, 해당 학원에 대해서 등록한 학원이 없다면 리뷰를 등록한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 1L)
    void registerReview_Success() throws Exception {
        // Given
        given(memberAccessService.existsMember(any())).willReturn(true);
        given(academyAccessService.existsAcademy(any())).willReturn(true);

        ReviewPostRequest request = ReviewFixture.makeReviewPostRequest(true);
        String jsonBody = objectMapper.writeValueAsString(request);

        // When
        ResultActions perform = mockMvc.perform(post("/reviews")
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.reviewId").isNumber())
                .andExpect(jsonPath("$.academyId").isNumber())
                .andDo(document("post-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                        ),
                        requestFields(
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("kindness").type(BOOLEAN).description("친절해요 리뷰 선택 여부"),
                                fieldWithPath("cheapFee").type(BOOLEAN).description("수강료가 싸요 리뷰 선택 여부"),
                                fieldWithPath("goodFacility").type(BOOLEAN).description("시설이 좋아요 리뷰 선택 여부"),
                                fieldWithPath("goodManagement").type(BOOLEAN).description("관리가 좋아요 리뷰 선택 여부"),
                                fieldWithPath("lovelyTeaching").type(BOOLEAN).description("가르침이 사랑스러워요 리뷰 선택 여부"),
                                fieldWithPath("shuttleAvailability").type(BOOLEAN).description("셔틀을 운행해요 리뷰 선택 여부")
                        ),
                        responseFields(
                                fieldWithPath("reviewId").type(NUMBER).description("리뷰 아이디"),
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디")
                        )
                ));
    }

    @Test
    @DisplayName("리뷰를 등록한 적 없다면 리뷰 등록 가능함을 응답한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 1L)
    void getReviewable_NotExistsReview_Reviewable() throws Exception {
        // Given
        given(memberAccessService.existsMember(any())).willReturn(true);
        given(academyAccessService.existsAcademy(any())).willReturn(true);

        // When
        ResultActions perform = mockMvc.perform(get("/reviews/reviewable")
                .param("academyId", String.valueOf(1L))
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt())
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.academyId").value(1L))
                .andExpect(jsonPath("$.reviewable").value(true))
                .andDo(document("get-reviewable",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                        ),
                        queryParameters(
                                parameterWithName("academyId").description("학원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("reviewable").type(BOOLEAN).description("리뷰 등록 가능 여부")
                        )
                ));
    }

}