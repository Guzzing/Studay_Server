package org.guzzing.studay_server.region.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class RegionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("시도를 파라미터로 요청하면 해당 시도, 시군구, 개수 데이터를 반환한다.")
    void getSubRegions_Sido_RegionResponse() throws Exception {
        // Given
        final String sido = "서울특별시";

        // When
        ResultActions perform = mockMvc.perform(get("/regions/beopjungdong")
                .param("sido", sido)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.targetRegion").value(sido))
                .andExpect(jsonPath("$.subRegion").isNotEmpty())
                .andExpect(jsonPath("$.subRegionCount").isNumber())
                .andDo(document("get-region-sigungu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("sido").description("시도")
                        ),
                        responseFields(
                                fieldWithPath("targetRegion").type(STRING).description("탐색한 시도명"),
                                fieldWithPath("subRegion").type(ARRAY).description("탐색한 시군구 조회 결과 리스트"),
                                fieldWithPath("subRegionCount").type(NUMBER).description("탐색한 시군구 조회 결과 수")
                        )
                ));
    }

    @Test
    @DisplayName("시도, 시군구를 요청 파라미터로 받아 해당 시도군구의 읍면동 데이터를 응답한다.")
    void getSubRegions_SidoAndSigungu_RegionResponse() throws Exception {
        // Given
        final String sido = "서울특별시";
        final String sigungu = "중구";

        // When
        ResultActions perform = mockMvc.perform(get("/regions/beopjungdong")
                .param("sido", sido)
                .param("sigungu", sigungu)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.targetRegion").value(sido + " " + sigungu))
                .andExpect(jsonPath("$.subRegion").isNotEmpty())
                .andExpect(jsonPath("$.subRegionCount").isNumber())
                .andDo(document("get-region-upmyeondong",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("sido").description("시도"),
                                parameterWithName("sigungu").description("시군구")
                        ),
                        responseFields(
                                fieldWithPath("targetRegion").type(STRING).description("탐색한 시도군구명"),
                                fieldWithPath("subRegion").type(ARRAY).description("탐색한 읍면동 조회 결과 리스트"),
                                fieldWithPath("subRegionCount").type(NUMBER).description("탐색한 읍면동 조회 결과 수")
                        )
                ));
    }
    
    @Test
    @DisplayName("아무런 파라미터 없이 요청하면 조회 가능한 시도 데이터를 반환한다.")
    void getSubRegions_None_RegionResponse() throws Exception {
        // Given & When
        ResultActions perform = mockMvc.perform(get("/regions/beopjungdong")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.targetRegion").value("전국"))
                .andExpect(jsonPath("$.subRegion").exists())
                .andExpect(jsonPath("$.subRegionCount").isNumber())
                .andDo(document("get-region-sido",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("targetRegion").type(STRING).description("전국"),
                                fieldWithPath("subRegion").type(ARRAY).description("탐색 가능한 시도 조회 결과 리스트"),
                                fieldWithPath("subRegionCount").type(NUMBER).description("탐색 가능한 시도 조회 결과 수")
                        )
                ));
    }

}