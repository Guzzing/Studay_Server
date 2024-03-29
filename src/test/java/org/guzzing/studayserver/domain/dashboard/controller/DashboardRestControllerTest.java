package org.guzzing.studayserver.domain.dashboard.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.nimbusds.oauth2.sdk.token.AccessTokenType.BEARER;
import static org.guzzing.studayserver.config.JwtTestConfig.AUTHORIZATION_HEADER;
import static org.guzzing.studayserver.testutil.fixture.dashboard.DashboardFixture.childId;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
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
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPutRequest;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.testutil.fixture.dashboard.DashboardFixture;
import org.guzzing.studayserver.testutil.security.WithMockCustomOAuth2LoginUser;
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
class DashboardRestControllerTest {

    private static final String TAG = "대시보드 API";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DashboardFixture dashboardFixture;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTestConfig jwtTestConfig;

    @MockBean
    private MemberAccessService memberAccessService;
    @MockBean
    private AcademyAccessService academyAccessService;
    @MockBean
    private ChildAccessService childAccessService;

    @Test
    @DisplayName("대시보드를 등록한다.")
    @WithMockCustomOAuth2LoginUser
    void registerDashboard_ReturnCreated() throws Exception {
        // Given
        final DashboardPostRequest request = dashboardFixture.makePostRequest();

        // When
        final ResultActions perform = mockMvc.perform(post("/dashboards")
                .content(objectMapper.writeValueAsBytes(request))
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.dashboardId").isNumber())
                .andExpect(jsonPath("$.childId").value(1L))
                .andExpect(jsonPath("$.lessonId").value(1L))
                .andDo(document("post-dashboard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("대시보드 등록")
                                .requestFields(
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("childId").type(NUMBER).description("아이 아이디"),
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("lessonId").type(NUMBER).description("수업 아이디"),
                                        fieldWithPath("schedules").type(ARRAY).description("일정 메모 목록"),
                                        fieldWithPath("schedules[].dayOfWeek").type(NUMBER).description("요일"),
                                        fieldWithPath("schedules[].startTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("schedules[].endTime").type(STRING).description("종료 시간"),
                                        fieldWithPath("paymentInfo").type(OBJECT).description("학원비 정보"),
                                        fieldWithPath("paymentInfo.educationFee").type(NUMBER).description("수강비"),
                                        fieldWithPath("paymentInfo.bookFee").type(NUMBER).description("교재비"),
                                        fieldWithPath("paymentInfo.shuttleFee").type(NUMBER).description("셔틀 버스 운행비"),
                                        fieldWithPath("paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                        fieldWithPath("paymentInfo.paymentDay").type(STRING).description("납부일"),
                                        fieldWithPath("simpleMemo").type(OBJECT).description("간단 메모 정보"),
                                        fieldWithPath("simpleMemo.kindness").type(BOOLEAN)
                                                .description("[친절함] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.goodFacility").type(BOOLEAN)
                                                .description("[좋은 시설] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.cheapFee").type(BOOLEAN)
                                                .description("[싼 학원비] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.goodManagement").type(BOOLEAN)
                                                .description("[좋은 관리] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.lovelyTeaching").type(BOOLEAN)
                                                .description("[사랑스런 교육] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.shuttleAvailability").type(BOOLEAN)
                                                .description("[셔틀 운행 여부] 간단 메모 선택 여부")
                                )
                                .responseFields(
                                        fieldWithPath("dashboardId").type(NUMBER).description("대시보드 아이디"),
                                        fieldWithPath("childId").type(NUMBER).description("아이 아이디"),
                                        fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("lessonId").type(NUMBER).description("수업 아이디")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("대시보드를 수정한다.")
    @WithMockCustomOAuth2LoginUser
    void updateDashboard_EditDashboard() throws Exception {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        final DashboardPutRequest request = dashboardFixture.makePutRequest();

        // When
        final ResultActions perform = mockMvc.perform(put("/dashboards/{dashboardId}", dashboard.getId())
                .content(objectMapper.writeValueAsBytes(request))
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.dashboardId").value(dashboard.getId()))
                .andExpect(jsonPath("$.paymentInfo").isNotEmpty())
                .andExpect(jsonPath("$.simpleMemo").isNotEmpty())
                .andDo(document("put-dashboard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("대시보드 수정")
                                .pathParameters(
                                        parameterWithName("dashboardId").description("대시보드 아이디")
                                )
                                .requestFields(
                                        fieldWithPath("paymentInfo").type(OBJECT).description("학원비 정보"),
                                        fieldWithPath("paymentInfo.educationFee").type(NUMBER).description("수강비"),
                                        fieldWithPath("paymentInfo.bookFee").type(NUMBER).description("교재비"),
                                        fieldWithPath("paymentInfo.shuttleFee").type(NUMBER).description("셔틀 버스 운행비"),
                                        fieldWithPath("paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                        fieldWithPath("paymentInfo.paymentDay").type(STRING).description("납부일"),
                                        fieldWithPath("simpleMemo").type(OBJECT).description("간단 메모 정보"),
                                        fieldWithPath("simpleMemo.kindness").type(BOOLEAN)
                                                .description("[친절함] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.goodFacility").type(BOOLEAN)
                                                .description("[좋은 시설] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.cheapFee").type(BOOLEAN)
                                                .description("[싼 학원비] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.goodManagement").type(BOOLEAN)
                                                .description("[좋은 관리] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.lovelyTeaching").type(BOOLEAN)
                                                .description("[사랑스런 교육] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.shuttleAvailability").type(BOOLEAN)
                                                .description("[셔틀 운행 여부] 간단 메모 선택 여부")
                                )
                                .responseFields(
                                        fieldWithPath("dashboardId").type(NUMBER).description("대시보드 아이디"),
                                        fieldWithPath("paymentInfo").type(OBJECT).description("학원비 정보"),
                                        fieldWithPath("paymentInfo.educationFee").type(NUMBER).description("수강비"),
                                        fieldWithPath("paymentInfo.bookFee").type(NUMBER).description("교재비"),
                                        fieldWithPath("paymentInfo.shuttleFee").type(NUMBER).description("셔틀 버스 운행비"),
                                        fieldWithPath("paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                        fieldWithPath("paymentInfo.paymentDay").type(STRING).description("납부일"),
                                        fieldWithPath("simpleMemo").type(OBJECT).description("간단 메모 정보"),
                                        fieldWithPath("simpleMemo.kindness").type(BOOLEAN)
                                                .description("[친절함] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.goodFacility").type(BOOLEAN)
                                                .description("[좋은 시설] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.cheapFee").type(BOOLEAN)
                                                .description("[싼 학원비] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.goodManagement").type(BOOLEAN)
                                                .description("[좋은 관리] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.lovelyTeaching").type(BOOLEAN)
                                                .description("[사랑스런 교육] 간단 메모 선택 여부"),
                                        fieldWithPath("simpleMemo.shuttleAvailability").type(BOOLEAN)
                                                .description("[셔틀 운행 여부] 간단 메모 선택 여부")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("아이디로 대시보드를 조회한다.")
    @WithMockCustomOAuth2LoginUser
    void getDashboard() throws Exception {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        // When
        final ResultActions perform = mockMvc.perform(get("/dashboards/{dashboardId}", dashboard.getId())
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.dashboardId").value(dashboard.getId()))
                .andExpect(jsonPath("$.childInfo.childId").value(dashboard.getChildId()))
                .andExpect(jsonPath("$.academyInfo.academyId").value(dashboard.getAcademyId()))
                .andExpect(jsonPath("$.lessonInfo.lessonId").value(dashboard.getLessonId()))
                .andExpect(jsonPath("$.schedules").isNotEmpty())
                .andExpect(jsonPath("$.paymentInfo.etcFee").isNumber())
                .andExpect(jsonPath("$.simpleMemo").exists())
                .andExpect(jsonPath("$.isActive").value(true))
                .andDo(document("get-dashboard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("대시보드 단건 조회")
                                .pathParameters(
                                        parameterWithName("dashboardId").description("대시보드 아이디")
                                )
                                .responseFields(
                                        fieldWithPath("dashboardId").type(NUMBER).description("대시보드 아이디"),
                                        fieldWithPath("childInfo").type(OBJECT).description("아이 정보"),
                                        fieldWithPath("childInfo.childId").type(NUMBER).description("아이 아이디"),
                                        fieldWithPath("childInfo.childNickName").type(STRING).description("아이 별칭"),
                                        fieldWithPath("academyInfo").type(OBJECT).description("학원 정보"),
                                        fieldWithPath("academyInfo.academyId").type(NUMBER).description("학원 아이디"),
                                        fieldWithPath("academyInfo.academyName").type(STRING).description("학원 이름"),
                                        fieldWithPath("academyInfo.contact").type(STRING).description("학원 연락처"),
                                        fieldWithPath("academyInfo.fullAddress").type(STRING).description("학원 주소"),
                                        fieldWithPath("academyInfo.shuttleAvailability").type(STRING)
                                                .description("셔틀 운행 여부"),
                                        fieldWithPath("academyInfo.expectedFee").type(NUMBER).description("예상 교육비"),
                                        fieldWithPath("academyInfo.updatedDate").type(STRING).description("업데이트 날짜"),
                                        fieldWithPath("academyInfo.categories").type(ARRAY)
                                                .description("강의 분야 구분"),
                                        fieldWithPath("lessonInfo").type(OBJECT).description("수업 정보"),
                                        fieldWithPath("lessonInfo.lessonId").type(NUMBER).description("수업 아이디"),
                                        fieldWithPath("lessonInfo.curriculum").type(STRING).description("수업 과목"),
                                        fieldWithPath("lessonInfo.capacity").type(NUMBER).description("수업 정원"),
                                        fieldWithPath("lessonInfo.duration").type(STRING).description("강의 기간"),
                                        fieldWithPath("lessonInfo.totalFee").type(NUMBER).description("총 수강료"),
                                        fieldWithPath("schedules").type(ARRAY).description("스케줄 메모 목록"),
                                        fieldWithPath("schedules[].dayOfWeek").type(NUMBER).description("요일"),
                                        fieldWithPath("schedules[].startTime").type(STRING).description("시작 시간"),
                                        fieldWithPath("schedules[].endTime").type(STRING).description("종료 시간"),
                                        fieldWithPath("paymentInfo").type(OBJECT).description("교육비 정보"),
                                        fieldWithPath("paymentInfo.educationFee").type(NUMBER).description("수강료"),
                                        fieldWithPath("paymentInfo.bookFee").type(NUMBER).description("교재비"),
                                        fieldWithPath("paymentInfo.shuttleFee").type(NUMBER).description("셔틀운행비"),
                                        fieldWithPath("paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                        fieldWithPath("paymentInfo.paymentDay").type(STRING).description("납부일"),
                                        fieldWithPath("simpleMemo").type(OBJECT).description("간편 메모"),
                                        fieldWithPath("simpleMemo.kindness").type(BOOLEAN).description("친절함 여부 메모"),
                                        fieldWithPath("simpleMemo.goodFacility").type(BOOLEAN)
                                                .description("좋은 시설 여부 메모"),
                                        fieldWithPath("simpleMemo.cheapFee").type(BOOLEAN)
                                                .description("값싼 교육비 여부 메모"),
                                        fieldWithPath("simpleMemo.goodManagement").type(BOOLEAN)
                                                .description("좋은 관리 여부 메모"),
                                        fieldWithPath("simpleMemo.lovelyTeaching").type(BOOLEAN)
                                                .description("사랑스런 교육 여부 메모"),
                                        fieldWithPath("simpleMemo.shuttleAvailability").type(BOOLEAN)
                                                .description("셔틀 운행 여부 메모"),
                                        fieldWithPath("isActive").type(BOOLEAN).description("활성화 여부"),
                                        fieldWithPath("isDeleted").type(BOOLEAN).description("삭제 여부")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("활성화 여부에 따라 아이의 모든 대시보드를 조회한다.")
    @WithMockCustomOAuth2LoginUser
    void getDashboards_ByActiveOnlyBoolean_AllDashboardOfChild() throws Exception {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        final boolean activeOnly = true;

        // When
        final ResultActions perform = mockMvc.perform(get("/dashboards")
                .param("childId", String.valueOf(childId))
                .param("active-only", String.valueOf(activeOnly))
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.responses").isArray())
                .andExpect(jsonPath("$.responses").isNotEmpty())
                .andExpect(jsonPath("$.responses[0].dashboardId").value(dashboard.getId()))
                .andExpect(jsonPath("$.responses[0].childInfo").exists())
                .andExpect(jsonPath("$.responses[0].childInfo.childId").value(dashboard.getChildId()))
                .andExpect(jsonPath("$.responses[0].academyInfo").exists())
                .andExpect(jsonPath("$.responses[0].academyInfo.academyId").value(dashboard.getAcademyId()))
                .andExpect(jsonPath("$.responses[0].lessonInfo.lessonId").value(dashboard.getLessonId()))
                .andExpect(jsonPath("$.responses[0].schedules").isArray())
                .andExpect(jsonPath("$.responses[0].schedules").isNotEmpty())
                .andExpect(jsonPath("$.responses[0].paymentInfo.etcFee").isNumber())
                .andExpect(jsonPath("$.responses[0].simpleMemo.kindness").isBoolean())
                .andExpect(jsonPath("$.responses[0].isActive").value(true))
                .andExpect(jsonPath("$.responses[0].isDeleted").value(false))
                .andDo(document("get-dashboards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                        .tag(TAG)
                                        .summary("아이 대시보드 전체 조회")
                                        .queryParameters(
                                                parameterWithName("childId").description("아이 아이디"),
                                                parameterWithName("active-only").description("활성화 여부 조회 조건(default = false)")
                                                        .optional()
                                        )
                                        .responseFields(
                                                fieldWithPath("responses").type(ARRAY).description("대시보드 조회 결과 목록"),
                                                fieldWithPath("responses[].dashboardId").type(NUMBER).description("대시보드 아이디"),
                                                fieldWithPath("responses[].childInfo").type(OBJECT).description("아이 정보"),
                                                fieldWithPath("responses[].childInfo.childId").type(NUMBER)
                                                        .description("아이 아이디"),
                                                fieldWithPath("responses[].childInfo.childNickName").type(STRING)
                                                        .description("아이 별칭"),
                                                fieldWithPath("responses[].academyInfo").type(OBJECT).description("학원 정보"),
                                                fieldWithPath("responses[].academyInfo.academyId").type(NUMBER)
                                                        .description("학원 아이디"),
                                                fieldWithPath("responses[].academyInfo.academyName").type(STRING)
                                                        .description("학원 이름"),
                                                fieldWithPath("responses[].academyInfo.contact").type(STRING)
                                                        .description("학원 연락처"),
                                                fieldWithPath("responses[].academyInfo.fullAddress").type(STRING)
                                                        .description("학원 주소"),
                                                fieldWithPath("responses[].academyInfo.shuttleAvailability").type(STRING)
                                                        .description("셔틀 운행 여부"),
                                                fieldWithPath("responses[].academyInfo.expectedFee").type(NUMBER)
                                                        .description("예상 교육비"),
                                                fieldWithPath("responses[].academyInfo.updatedDate").type(STRING)
                                                        .description("업데이트 날짜"),
                                                fieldWithPath("responses[].academyInfo.categories").type(ARRAY)
                                                        .description("강의 분야 구분"),
                                                fieldWithPath("responses[].lessonInfo").type(OBJECT).description("수업 정보"),
                                                fieldWithPath("responses[].lessonInfo.lessonId").type(NUMBER)
                                                        .description("수업 아이디"),
                                                fieldWithPath("responses[].lessonInfo.curriculum").type(STRING)
                                                        .description("수업 과목"),
                                                fieldWithPath("responses[].lessonInfo.capacity").type(NUMBER)
                                                        .description("수업 정원"),
                                                fieldWithPath("responses[].lessonInfo.duration").type(STRING)
                                                        .description("강의 기간"),
                                                fieldWithPath("responses[].lessonInfo.totalFee").type(NUMBER)
                                                        .description("총 수강료"),
                                                fieldWithPath("responses[].schedules").type(ARRAY).description("스케줄 메모 목록"),
                                                fieldWithPath("responses[].schedules[].dayOfWeek").type(NUMBER)
                                                        .description("요일"),
                                                fieldWithPath("responses[].schedules[].startTime").type(STRING)
                                                        .description("시작 시간"),
                                                fieldWithPath("responses[].schedules[].endTime").type(STRING)
                                                        .description("종료 시간"),
//                                        fieldWithPath("responses[].schedules[].repeatance").type(STRING).description("반복 주기 종류"),
                                                fieldWithPath("responses[].paymentInfo").type(OBJECT).description("교육비 정보"),
                                                fieldWithPath("responses[].paymentInfo.educationFee").type(NUMBER)
                                                        .description("수강료"),
                                                fieldWithPath("responses[].paymentInfo.bookFee").type(NUMBER)
                                                        .description("교재비"),
                                                fieldWithPath("responses[].paymentInfo.shuttleFee").type(NUMBER)
                                                        .description("셔틀운행비"),
                                                fieldWithPath("responses[].paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                                fieldWithPath("responses[].paymentInfo.paymentDay").type(STRING)
                                                        .description("납부일"),
                                                fieldWithPath("responses[].simpleMemo").type(OBJECT).description("간편 메모"),
                                                fieldWithPath("responses[].simpleMemo.kindness").type(BOOLEAN)
                                                        .description("친절함 여부 메모"),
                                                fieldWithPath("responses[].simpleMemo.goodFacility").type(BOOLEAN)
                                                        .description("좋은 시설 여부 메모"),
                                                fieldWithPath("responses[].simpleMemo.cheapFee").type(BOOLEAN)
                                                        .description("값싼 교육비 여부 메모"),
                                                fieldWithPath("responses[].simpleMemo.goodManagement").type(BOOLEAN)
                                                        .description("좋은 관리 여부 메모"),
                                                fieldWithPath("responses[].simpleMemo.lovelyTeaching").type(BOOLEAN)
                                                        .description("사랑스런 교육 여부 메모"),
                                                fieldWithPath("responses[].simpleMemo.shuttleAvailability").type(BOOLEAN)
                                                        .description("셔틀 운행 여부 메모"),
                                                fieldWithPath("responses[].isActive").type(BOOLEAN).description("활성화 여부"),
                                                fieldWithPath("responses[].isDeleted").type(BOOLEAN).description("삭제 여부")
                                        )
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("비활성화된 대시보드를 제거한다.")
    @WithMockCustomOAuth2LoginUser
    void removeDashboard_InActiveDashboard_Success() throws Exception {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final Dashboard dashboard = dashboardFixture.createNotActiveEntity();

        // When
        final ResultActions perform = mockMvc.perform(patch("/dashboards/{dashboardId}", dashboard.getId())
                .header(String.valueOf(AUTHORIZATION_HEADER), BEARER)
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-dashboard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("대시보드 제거")
                                .pathParameters(
                                        parameterWithName("dashboardId").description("대시보드 아이디")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("대시보의 활성화여부를 반전한다.")
    @WithMockCustomOAuth2LoginUser
    void revertToggleActive_Dashboard() throws Exception {
        // Given
        given(childAccessService.findChildInfo(anyLong())).willReturn(dashboardFixture.makeChildInfo());
        given(academyAccessService.findAcademyInfo(anyLong())).willReturn(dashboardFixture.makeAcademyInfo());
        given(academyAccessService.findLessonInfo(anyLong())).willReturn(dashboardFixture.makeLessonInfo());

        final Dashboard dashboard = dashboardFixture.createActiveEntity();

        // When
        final ResultActions perform = mockMvc.perform(patch("/dashboards/{dashboardId}/toggle", dashboard.getId())
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE));

        // Then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.dashboardId").value(dashboard.getId()))
                .andExpect(jsonPath("$.isActive").value(false))
                .andDo(document("toggle-dashboard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag(TAG)
                                .summary("대시보드 활성화 반전")
                                .pathParameters(
                                        parameterWithName("dashboardId").description("대시보드 아이디")
                                )
                                .responseFields(
                                        fieldWithPath("dashboardId").type(NUMBER).description("대시보드 아이디"),
                                        fieldWithPath("isActive").type(BOOLEAN).description("대시보드 활성화 여부")
                                )
                                .build()
                        )
                ));
    }

}
