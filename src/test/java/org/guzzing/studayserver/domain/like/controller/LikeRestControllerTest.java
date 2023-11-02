package org.guzzing.studayserver.domain.like.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.like.controller.dto.LikeRequest;
import org.guzzing.studayserver.testutil.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@ActiveProfiles(profiles = {"dev", "oauth"})
class LikeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestConfig testConfig;

    private final Long academyId = 1L;

    @Test
    @DisplayName("헤더에 JWT 로 들어오는 멤버 아이디와 바디로 전달되는 학원 아이디를 이용해서 좋아요를 등록한다.")
    void createLike_MemberIdAndAcademyId_RegisterLike() throws Exception {
        // Given
        LikeRequest request = new LikeRequest(academyId);
        String jsonBody = objectMapper.writeValueAsString(request);

        // When
        ResultActions perform = mockMvc.perform(post("/likes")
                .header("Authorization", "Bearer " + testConfig.getJwtToken())
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
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                        ),
                        requestFields(
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디")
                        ),
                        responseFields(
                                fieldWithPath("likeId").type(NUMBER).description("좋아요 아이디"),
                                fieldWithPath("memberId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디")
                        )
                ));
    }

}