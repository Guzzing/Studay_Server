package org.guzzing.studayserver.domain.child.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Stream;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.guzzing.studayserver.domain.child.controller.request.ChildModifyRequest;
import org.guzzing.studayserver.domain.child.controller.response.ChildrenFindResponse;
import org.guzzing.studayserver.domain.child.provider.ProfileImageProvider;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.result.ChildProfileImagePatchResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(ChildRestController.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
class ChildRestControllerTest {

    private static final String TAG = "아이 API";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildService childService;
    @MockBean
    private ChildRepository childRepository;
    @MockBean
    private ProfileImageProvider profileImageProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class Create {

        @DisplayName("아이 생성시 정상 값이면 OK를 반환한다.")
        @Test
        @WithMockCustomOAuth2LoginUser(memberId = 1L)
        void statusIsOk() throws Exception {
            // Given
            ChildCreateRequest request = new ChildCreateRequest("childName1", "초등학교 1학년");
            Long expectedChildId = 2L;

            // When
            when(childService.create(any(ChildCreateParam.class), anyLong())).thenReturn(expectedChildId);

            // Then
            mockMvc.perform(post("/children")
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @DisplayName("아이 생성시 잘못된 요청은 400에러를 반환한다.")
        @ParameterizedTest
        @WithMockCustomOAuth2LoginUser(memberId = 1L)
        @MethodSource("provideInvalidRequests")
        void statusIsBadRequest(ChildCreateRequest invalidRequest) throws Exception {
            // Then
            mockMvc.perform(post("/children")
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }


        private static Stream<Arguments> provideInvalidRequests() {
            return Stream.of(
                    // 빈 닉네임
                    Arguments.of(new ChildCreateRequest("", "초등학교 1학년")),

                    // 긴 닉네임
                    Arguments.of(new ChildCreateRequest("a".repeat(11), "초등학교 1학년")),

                    // 빈 학년
                    Arguments.of(new ChildCreateRequest("a".repeat(11), ""))
            );
        }
    }

    @DisplayName("멤버에 할당된 아이들의 정보를 반환한다.")
    @WithMockCustomOAuth2LoginUser(memberId = 1L)
    @Test
    void findChildren_success() throws Exception {
        // Given
        final String profileImageUrl1 = "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/default-profile-image/icon_0_0.png";
        final String profileImageUrl2 = "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/default-profile-image/icon_2_1.png";
        ChildrenFindResult result = new ChildrenFindResult(List.of(
                new ChildFindResult(1L, profileImageUrl1, "Nickname1", "초등학교 1학년", "휴식 중!"),
                new ChildFindResult(2L, profileImageUrl2, "Nickname2", "초등학교 2학년", "휴식 중!")
        ));

        given(childService.findByMemberId(1L)).willReturn(result);

        ChildrenFindResponse response = ChildrenFindResponse.from(result);

        // When & Then
        mockMvc.perform(get("/children")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @DisplayName("아이를 삭제한다.")
    @WithMockCustomOAuth2LoginUser()
    @Test
    void delete_success() throws Exception {
        // Given
        Long existingChildId = 200L;

        // When & Then
        mockMvc.perform(delete("/children/{childId}", existingChildId))
                .andExpect(status().isNoContent());
    }

    @Nested
    class modify {

        @DisplayName("아이의 정보를 수정한다.")
        @WithMockCustomOAuth2LoginUser(memberId = 1L)
        @Test
        void success() throws Exception {
            // Given
            Long childId = 100L;

            ChildModifyRequest request = new ChildModifyRequest("아이 닉네임", "초등학교 1학년");

            given(childService.modify(any())).willReturn(childId);

            // Then
            mockMvc.perform(patch("/children/{childId}", childId)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().string(childId.toString()));
        }

        @DisplayName("잘못된 요청이 들어오면 예외를 발생시킨다.")
        @WithMockCustomOAuth2LoginUser()
        @ParameterizedTest
        @MethodSource("provideInvalidRequests")
        void givenInvalidRequest_throwsException(ChildModifyRequest invalidRequest) throws Exception {
            // When & Then
            mockMvc.perform(patch("/children/{childId}", 100L)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @Disabled
        @DisplayName("아이 프로필을 변경한다.")
        void modifyProfileImage() throws Exception {
            // Given
            long childId = 24L;
            when(childService.modifyProfileImage(any(Long.class), any(MultipartFile.class)))
                    .thenReturn(new ChildProfileImagePatchResult(childId,
                            "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/default-profile-image/test.png"));

            MockMultipartFile file = new MockMultipartFile(
                    "file",          // query parameter 이름
                    "filename.txt",  // 파일 이름
                    MediaType.IMAGE_PNG_VALUE, // 파일 타입
                    "file content".getBytes()  // 파일 내용
            );

            // When
            ResultActions perform = mockMvc.perform(RestDocumentationRequestBuilders
                    .multipart("/children/{childId}/profile", childId)
                    .file(file)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE)
            );

            // Then
            perform.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.childId").value(childId))
                    .andExpect(jsonPath("$.profileImageUrl")
                            .value("https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/default-profile-image/test.png"))
                    .andDo(document("patch-child-profile",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            resource(ResourceSnippetParameters.builder()
                                    .tag(TAG)
                                    .summary("아이 프로필 변경")
                                    .pathParameters(
                                            parameterWithName("childId").description("아이 아이디")
                                    )
                                    .queryParameters(
                                            parameterWithName("file").description("프로필 파일")
                                    )
                                    .responseFields(
                                            fieldWithPath("childId").type(NUMBER).description("아이 아이디"),
                                            fieldWithPath("profileImageUrl").type(STRING).description("프로필 URL")
                                    )
                                    .build()
                            )
                    ));
        }

        private static Stream<Arguments> provideInvalidRequests() {
            return Stream.of(
                    // 빈 닉네임
                    Arguments.of(new ChildModifyRequest("", "초등학교 1학년")),

                    // 긴 닉네임
                    Arguments.of(new ChildModifyRequest("a".repeat(11), "초등학교 1학년")),

                    // 빈 학년
                    Arguments.of(new ChildModifyRequest("a".repeat(11), ""))
            );
        }
    }
}
