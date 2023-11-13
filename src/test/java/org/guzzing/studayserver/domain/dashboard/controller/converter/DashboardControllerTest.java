package org.guzzing.studayserver.domain.dashboard.controller.converter;

import static org.guzzing.studayserver.testutil.fixture.TestConfig.AUTHORIZATION_HEADER;
import static org.guzzing.studayserver.testutil.fixture.TestConfig.BEARER;
import static org.mockito.ArgumentMatchers.anyLong;
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
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.fixture.DashboardFixture;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.guzzing.studayserver.testutil.fixture.TestConfig;
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
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DashboardFixture dashboardFixture;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestConfig testConfig;

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
                .header(AUTHORIZATION_HEADER, TestConfig.BEARER + testConfig.getJwt())
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
                        requestFields(
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("childId").type(NUMBER).description("아이 아이디"),
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("lessonId").type(NUMBER).description("수업 아이디"),
                                fieldWithPath("schedules").type(ARRAY).description("일정 메모 목록"),
                                fieldWithPath("schedules[].dayOfWeek").type(NUMBER).description("요일"),
                                fieldWithPath("schedules[].startTime").type(STRING).description("시작 시간"),
                                fieldWithPath("schedules[].endTime").type(STRING).description("종료 시간"),
                                fieldWithPath("schedules[].repeatance").type(STRING).description("반복 종류"),
                                fieldWithPath("paymentInfo").type(OBJECT).description("학원비 정보"),
                                fieldWithPath("paymentInfo.educationFee").type(NUMBER).description("수강비"),
                                fieldWithPath("paymentInfo.bookFee").type(NUMBER).description("교재비"),
                                fieldWithPath("paymentInfo.shuttleFee").type(NUMBER).description("셔틀 버스 운행비"),
                                fieldWithPath("paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                fieldWithPath("paymentInfo.paymentDay").type(STRING).description("납부일"),
                                fieldWithPath("simpleMemo").type(OBJECT).description("간단 메모 정보"),
                                fieldWithPath("simpleMemo.kindness").type(BOOLEAN).description("[친절함] 간단 메모 선택 여부"),
                                fieldWithPath("simpleMemo.goodFacility").type(BOOLEAN)
                                        .description("[좋은 시설] 간단 메모 선택 여부"),
                                fieldWithPath("simpleMemo.cheapFee").type(BOOLEAN).description("[싼 학원비] 간단 메모 선택 여부"),
                                fieldWithPath("simpleMemo.goodManagement").type(BOOLEAN)
                                        .description("[좋은 관리] 간단 메모 선택 여부"),
                                fieldWithPath("simpleMemo.lovelyTeaching").type(BOOLEAN)
                                        .description("[사랑스런 교육] 간단 메모 선택 여부"),
                                fieldWithPath("simpleMemo.shuttleAvailability").type(BOOLEAN)
                                        .description("[셔틀 운행 여부] 간단 메모 선택 여부")
                        ),
                        responseFields(
                                fieldWithPath("dashboardId").type(NUMBER).description("대시보드 아이디"),
                                fieldWithPath("childId").type(NUMBER).description("아이 아이디"),
                                fieldWithPath("academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("lessonId").type(NUMBER).description("수업 아이디")
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
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt())
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
                .andExpect(jsonPath("$.active").value(true))
                .andDo(document("get-dashboard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰 (Bearer)")
                        ),
                        pathParameters(
                                parameterWithName("dashboardId").description("대시보드 아이디")
                        ),
                        responseFields(
                                fieldWithPath("dashboardId").type(NUMBER).description("대시보드 아이디"),
                                fieldWithPath("childInfo").type(OBJECT).description("아이 정보"),
                                fieldWithPath("childInfo.childId").type(NUMBER).description("아이 아이디"),
                                fieldWithPath("childInfo.childNickName").type(STRING).description("아이 별칭"),
                                fieldWithPath("academyInfo").type(OBJECT).description("학원 정보"),
                                fieldWithPath("academyInfo.academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("academyInfo.academyName").type(STRING).description("학원 이름"),
                                fieldWithPath("academyInfo.address").type(STRING).description("학원 주소"),
                                fieldWithPath("lessonInfo").type(OBJECT).description("수업 정보"),
                                fieldWithPath("lessonInfo.lessonId").type(NUMBER).description("수업 아이디"),
                                fieldWithPath("lessonInfo.subject").type(STRING).description("수업 과목"),
                                fieldWithPath("schedules").type(ARRAY).description("스케줄 메모 목록"),
                                fieldWithPath("schedules[].dayOfWeek").type(NUMBER).description("요일"),
                                fieldWithPath("schedules[].startTime").type(STRING).description("시작 시간"),
                                fieldWithPath("schedules[].endTime").type(STRING).description("종료 시간"),
                                fieldWithPath("schedules[].repeatance").type(STRING)
                                        .description("반복 주기 종류"),
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
                                fieldWithPath("active").type(BOOLEAN).description("활성화 여부"),
                                fieldWithPath("deleted").type(BOOLEAN).description("삭제 여부")
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
                .header(AUTHORIZATION_HEADER, BEARER + testConfig.getJwt())
                .param("childId", String.valueOf(DashboardFixture.childId))
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
                .andExpect(jsonPath("$.responses[0].active").value(true))
                .andExpect(jsonPath("$.responses[0].deleted").value(false))
                .andDo(document("get-dashboards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(AUTHORIZATION_HEADER).description("JWT 토큰")
                        ),
                        queryParameters(
                                parameterWithName("childId").description("아이 아이디"),
                                parameterWithName("active-only").description("활성화 여부 조회 조건(default = false)")
                        ),
                        responseFields(
                                fieldWithPath("responses").type(ARRAY).description("대시보드 조회 결과 목록"),
                                fieldWithPath("responses[].dashboardId").type(NUMBER).description("대시보드 아이디"),
                                fieldWithPath("responses[].childInfo").type(OBJECT).description("아이 정보"),
                                fieldWithPath("responses[].childInfo.childId").type(NUMBER).description("아이 아이디"),
                                fieldWithPath("responses[].childInfo.childNickName").type(STRING).description("아이 별칭"),
                                fieldWithPath("responses[].academyInfo").type(OBJECT).description("학원 정보"),
                                fieldWithPath("responses[].academyInfo.academyId").type(NUMBER).description("학원 아이디"),
                                fieldWithPath("responses[].academyInfo.academyName").type(STRING).description("학원 이름"),
                                fieldWithPath("responses[].academyInfo.address").type(STRING).description("학원 주소"),
                                fieldWithPath("responses[].lessonInfo").type(OBJECT).description("수업 정보"),
                                fieldWithPath("responses[].lessonInfo.lessonId").type(NUMBER).description("수업 아이디"),
                                fieldWithPath("responses[].lessonInfo.subject").type(STRING).description("수업 과목"),
                                fieldWithPath("responses[].schedules").type(ARRAY).description("스케줄 메모 목록"),
                                fieldWithPath("responses[].schedules[].dayOfWeek").type(NUMBER).description("요일"),
                                fieldWithPath("responses[].schedules[].startTime").type(STRING).description("시작 시간"),
                                fieldWithPath("responses[].schedules[].endTime").type(STRING).description("종료 시간"),
                                fieldWithPath("responses[].schedules[].repeatance").type(STRING)
                                        .description("반복 주기 종류"),
                                fieldWithPath("responses[].paymentInfo").type(OBJECT).description("교육비 정보"),
                                fieldWithPath("responses[].paymentInfo.educationFee").type(NUMBER).description("수강료"),
                                fieldWithPath("responses[].paymentInfo.bookFee").type(NUMBER).description("교재비"),
                                fieldWithPath("responses[].paymentInfo.shuttleFee").type(NUMBER).description("셔틀운행비"),
                                fieldWithPath("responses[].paymentInfo.etcFee").type(NUMBER).description("기타비"),
                                fieldWithPath("responses[].paymentInfo.paymentDay").type(STRING).description("납부일"),
                                fieldWithPath("responses[].simpleMemo").type(OBJECT).description("간편 메모"),
                                fieldWithPath("responses[].simpleMemo.kindness").type(BOOLEAN).description("친절함 여부 메모"),
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
                                fieldWithPath("responses[].active").type(BOOLEAN).description("활성화 여부"),
                                fieldWithPath("responses[].deleted").type(BOOLEAN).description("삭제 여부")
                        )
                ));
    }

}