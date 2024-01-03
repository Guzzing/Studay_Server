package org.guzzing.studayserver.domain.like.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.guzzing.studayserver.config.JwtTestConfig.AUTHORIZATION_HEADER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.config.JwtTestConfig;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.like.controller.dto.request.LikePostRequest;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.guzzing.studayserver.testutil.fixture.member.MemberFixture;
import org.guzzing.studayserver.testutil.security.WithMockCustomOAuth2LoginUser;
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

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestMethodOrder(value = OrderAnnotation.class)
@SpringBootTest
@Transactional
class LikeRestControllerTest {

    private static final String TAG = "좋아요 API";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTestConfig jwtTestConfig;

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AcademyRepository academyRepository;

    private Member savedMember;
    private Academy savedAcademy;
    private Like like;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(MemberFixture.makeMemberEntity());
        savedAcademy = academyRepository.save(AcademyFixture.academySungnam());
        like = Like.of(savedMember, savedAcademy);
    }

    @Test
    @Order(1)
    @DisplayName("헤더에 JWT 로 들어오는 멤버 아이디와 바디로 전달되는 학원 아이디를 이용해서 좋아요를 등록한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 1)
    void createLike_MemberIdAndAcademyId_RegisterLike() throws Exception {
        // Given
        LikePostRequest request = new LikePostRequest(like.getAcademyId());
        String jsonBody = objectMapper.writeValueAsString(request);

        // When
        ResultActions perform = mockMvc.perform(post("/likes")
                .header(AUTHORIZATION_HEADER, jwtTestConfig.getJwt())
                .param("memberId", String.valueOf(savedMember.getId()))
                .content(jsonBody)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.likeId").isNumber())
                .andExpect(jsonPath("$.memberId").value(like.getMemberId()))
                .andExpect(jsonPath("$.academyId").value(like.getAcademyId()))
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
    @Order(2)
    @DisplayName("등록한 좋아요를 제거한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 2)
    void removeLike_LikeId_Remove() throws Exception {
        // Given
        Like savedLike = likeRepository.save(like);

        // When
        ResultActions perform = mockMvc.perform(delete("/likes/{likeId}", savedLike.getId())
                .header(AUTHORIZATION_HEADER, jwtTestConfig.getJwt())
                .param("memberId", String.valueOf(savedMember.getId())));

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
    @Order(value = 3)
    @DisplayName("학원 아이디로 좋아요를 삭제한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 3)
    void removeLikeOfAcademy_AcademyId_Delete() throws Exception {
        // Given
        likeRepository.save(like);

        // When
        ResultActions perform = mockMvc.perform(delete("/likes")
                .header(AUTHORIZATION_HEADER, jwtTestConfig.getJwt())
                .queryParam("academyId", String.valueOf(savedMember.getId())));

        // Then
        perform.andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-like-academyId",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
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
    @Order(value = 4)
    @DisplayName("좋아요한 학원 비용 정보를 응답받는다.")
    @WithMockCustomOAuth2LoginUser(memberId = 4)
    void getAllLikes_MemberId() throws Exception {
        // Given
        likeRepository.save(like);

        // When
        ResultActions perform = mockMvc.perform(get("/likes")
                .header(AUTHORIZATION_HEADER, jwtTestConfig.getJwt())
                .param("memberId", String.valueOf(savedMember.getId()))
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
