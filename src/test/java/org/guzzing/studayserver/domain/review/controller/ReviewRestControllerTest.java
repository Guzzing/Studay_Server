package org.guzzing.studayserver.domain.review.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.guzzing.studayserver.testutil.JwtTestConfig.AUTHORIZATION_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.review.controller.dto.request.ReviewPostRequest;
import org.guzzing.studayserver.domain.review.fixture.ReviewFixture;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.guzzing.studayserver.testutil.JwtTestConfig;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.guzzing.studayserver.testutil.fixture.member.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@Transactional
class ReviewRestControllerTest {

    private static final String TAG = "리뷰 API";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTestConfig jwtTestConfig;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AcademyRepository academyRepository;
    @Autowired
    private ReviewCountRepository reviewCountRepository;

    private Academy savedAcademy;

    @BeforeEach
    void setUp() {
        memberRepository.save(MemberFixture.makeMemberEntity());
        savedAcademy = academyRepository.save(AcademyFixture.academySungnam());
        reviewCountRepository.save(ReviewCount.makeDefaultReviewCount(savedAcademy));
    }

    @Test
    @Order(value = 1)
    @DisplayName("리뷰 타입이 3개 이하고, 해당 학원에 대해서 등록한 학원이 없다면 리뷰를 등록한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 1)
    void registerReview_Success() throws Exception {
        // Given
        ReviewPostRequest request = ReviewFixture.makeReviewPostRequest(savedAcademy.getId(),
                ReviewFixture.makeValidReviewMap());
        String jsonBody = objectMapper.writeValueAsString(request);

        // When
        ResultActions perform = mockMvc.perform(post("/reviews")
                .header(AUTHORIZATION_HEADER, jwtTestConfig.getJwt())
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.reviewId").isNumber())
                .andExpect(jsonPath("$.academyId").value(savedAcademy.getId()))
                .andDo(document("post-review",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("리뷰 등록")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                                )
                                .requestFields(
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("kindness").type(BOOLEAN).description("친절해요 리뷰 선택 여부"),
                                        fieldWithPath("cheapFee").type(BOOLEAN).description("수강료가 싸요 리뷰 선택 여부"),
                                        fieldWithPath("goodFacility").type(BOOLEAN).description("시설이 좋아요 리뷰 선택 여부"),
                                        fieldWithPath("goodManagement").type(BOOLEAN).description("관리가 좋아요 리뷰 선택 여부"),
                                        fieldWithPath("lovelyTeaching").type(BOOLEAN)
                                                .description("가르침이 사랑스러워요 리뷰 선택 여부"),
                                        fieldWithPath("shuttleAvailability").type(BOOLEAN)
                                                .description("셔틀을 운행해요 리뷰 선택 여부")
                                )
                                .responseFields(
                                        fieldWithPath("reviewId").type(NUMBER).description("리뷰 아이디"),
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @Order(value = 2)
    @DisplayName("리뷰를 등록한 적 없다면 리뷰 등록 가능함을 응답한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 2)
    void getReviewable_NotExistsReview_Reviewable() throws Exception {
        // When
        ResultActions perform = mockMvc.perform(get("/reviews/reviewable")
                .param("academyId", String.valueOf(savedAcademy.getId()))
                .header(AUTHORIZATION_HEADER, jwtTestConfig.getJwt())
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.academyId").value(savedAcademy.getId()))
                .andExpect(jsonPath("$.reviewable").value(true))
                .andDo(document("get-reviewable",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("리뷰 가능 여부 확인")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                                )
                                .queryParameters(
                                        parameterWithName("academyId").description("학원 아이디")
                                )
                                .responseFields(
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("reviewable").type(BOOLEAN).description("리뷰 등록 가능 여부")
                                )
                                .build()
                        )
                ));
    }

}
