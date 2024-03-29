package org.guzzing.studayserver.domain.region.controller;

import static org.guzzing.studayserver.testutil.fixture.region.RegionFixture.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.transaction.Transactional;
import org.guzzing.studayserver.domain.region.model.Region;
import org.guzzing.studayserver.domain.region.repository.RegionRepository;
import org.guzzing.studayserver.testutil.fixture.region.RegionFixture;
import org.guzzing.studayserver.testutil.security.WithMockCustomOAuth2LoginUser;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        Region region = RegionFixture.makeRegionEntity();

        regionRepository.save(region);
    }

    @Test
    @DisplayName("아무런 파라미터 없이 요청하면 조회 가능한 시도 데이터를 반환한다.")
    @WithMockCustomOAuth2LoginUser
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
                .andDo(document("get-region-beopjungdong-sido",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("targetRegion").type(STRING).description("전국"),
                                fieldWithPath("subRegion").type(ARRAY).description("탐색 가능한 시도 조회 결과 리스트"),
                                fieldWithPath("subRegionCount").type(NUMBER).description("탐색 가능한 시도 조회 결과 수")
                        )
                ));
    }

    @Test
    @DisplayName("시도를 파라미터로 요청하면 해당 시도, 시군구, 개수 데이터를 반환한다.")
    @WithMockCustomOAuth2LoginUser
    void getSubRegions_Sido_RegionResponse() throws Exception {
        // Given & When
        ResultActions perform = mockMvc.perform(get("/regions/beopjungdong/{sido}", SIDO)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.targetRegion").value(SIDO))
                .andExpect(jsonPath("$.subRegion").isNotEmpty())
                .andExpect(jsonPath("$.subRegionCount").isNumber())
                .andDo(document("get-region-beopjungdong-sigungu",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
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
    @WithMockCustomOAuth2LoginUser
    void getSubRegions_SidoAndSigungu_RegionResponse() throws Exception {
        // Given & When
        ResultActions perform = mockMvc.perform(get("/regions/beopjungdong/{sido}/{sigungu}", SIDO, SIGUNGU)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.targetRegion").value(SIDO + " " + SIGUNGU))
                .andExpect(jsonPath("$.subRegion").isNotEmpty())
                .andExpect(jsonPath("$.subRegionCount").isNumber())
                .andDo(document("get-region-beopjungdong-upmyeondong",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
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
    @DisplayName("시도, 시군구, 읍면동 데이터를 요청받아, 해당하는 위경도 데이터를 응답한다.")
    @WithMockCustomOAuth2LoginUser
    void getLocation_AllAddress_RegionLocationResponse() throws Exception {
        // Given & When
        ResultActions perform = mockMvc.perform(get("/regions/location")
                .param("sido", SIDO)
                .param("sigungu", SIGUNGU)
                .param("upmyeondong", UPMYEONDONG)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.sido").value(SIDO))
                .andExpect(jsonPath("$.sigungu").value(SIGUNGU))
                .andExpect(jsonPath("$.upmyeondong").value(UPMYEONDONG))
                .andExpect(jsonPath("$.latitude").isNumber())
                .andExpect(jsonPath("$.longitude").isNumber())
                .andDo(document("get-region-location",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("sido").description("시도"),
                                parameterWithName("sigungu").description("시군구"),
                                parameterWithName("upmyeondong").description("읍면동")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("조회된 법정동 코드"),
                                fieldWithPath("sido").type(STRING).description("조회된 시도"),
                                fieldWithPath("sigungu").type(STRING).description("조회된 시군구"),
                                fieldWithPath("upmyeondong").type(STRING).description("조회된 읍면동"),
                                fieldWithPath("latitude").type(NUMBER).description("법정동 위도"),
                                fieldWithPath("longitude").type(NUMBER).description("법정동 경도")
                        )
                ));
    }

    @Test
    @DisplayName("위도, 경도가 주어지면 해당 시도, 시군구, 읍면동을 반환한다.")
    @WithMockCustomOAuth2LoginUser
    void getRegionName_withLatAndLng_RegionNameResponse() throws Exception {
        ResultActions perform = mockMvc.perform(get("/regions")
            .param("lat", String.valueOf(LATITUDE))
            .param("lng", String.valueOf(LONGITUDE)));

        // Then
        perform.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.sido").value(SIDO))
            .andExpect(jsonPath("$.sigungu").value(SIGUNGU))
            .andExpect(jsonPath("$.upmyeondong").value(UPMYEONDONG))
            .andDo(document("get-region-name",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                queryParameters(
                    parameterWithName("lat").description("위도"),
                    parameterWithName("lng").description("경도")
                ),
                responseFields(
                    fieldWithPath("sido").type(STRING).description("조회된 시도"),
                    fieldWithPath("sigungu").type(STRING).description("조회된 시군구"),
                    fieldWithPath("upmyeondong").type(STRING).description("조회된 읍면동")
                )
            ));
    }

}
