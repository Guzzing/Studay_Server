package org.guzzing.studayserver.domain.child.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.guzzing.studayserver.domain.child.service.ChildService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChildRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChildRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildService childService;

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
            when(childService.create(any())).thenReturn(expectedChildId);

            // Then
            mockMvc.perform(post("/children")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @DisplayName("아이 생성시 잘못된 요청은 400에러를 반환한다.")
        @ParameterizedTest
        @WithMockCustomOAuth2LoginUser(memberId = 1L)
        @MethodSource("provideInvalidRequests")
        void statusIsBadRequest(ChildCreateRequest invalidRequest) throws Exception {
            // Then
            mockMvc.perform(post("/children")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
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

}
