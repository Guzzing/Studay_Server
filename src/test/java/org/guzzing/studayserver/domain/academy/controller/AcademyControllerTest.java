package org.guzzing.studayserver.domain.academy.controller;

import org.guzzing.studayserver.domain.academy.controller.dto.response.AcademyGetResponse;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.AcademyGetResult;
import org.guzzing.studayserver.domain.testutil.controller.BaseControllerTest;
import org.guzzing.studayserver.domain.testutil.fixture.AcademyFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;


class AcademyControllerTest extends BaseControllerTest {

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ReviewCountRepository reviewCountRepository;

    private Academy savedAcademyAboutSungnam;

    private Lesson savedALessonAboutSungnam;

    private ReviewCount savedReviewCountAboutSungnam;

    @BeforeEach
    void setUp() {
        Academy academyAboutSungnam = AcademyFixture.academySungnam();
        academyAboutSungnam.changeEducationFee(100000L);
        savedAcademyAboutSungnam = academyRepository.save(academyAboutSungnam);

        Lesson lessonAboutSungnam = AcademyFixture.lessonForSunganm(savedAcademyAboutSungnam);
        savedALessonAboutSungnam = lessonRepository.save(lessonAboutSungnam);

        savedReviewCountAboutSungnam = reviewCountRepository.save(AcademyFixture.reviewCountDefault(savedAcademyAboutSungnam));

    }

    @Test
    @DisplayName("학원 상세 조회 api이 올바르게 들어가는지 확인합니다.")
    void getAcademy() {
        AcademyGetResult academyGetResult =  AcademyGetResult.of(
                savedAcademyAboutSungnam ,
                List.of(
                        savedALessonAboutSungnam
                ),
                savedReviewCountAboutSungnam);

        AcademyGetResponse academyGetResponse = AcademyGetResponse.of(academyGetResult);

        given(this.spec)
                .filter(document(DEFAULT_RESTDOC_PATH,
                        pathParameters(
                                parameterWithName("academyId").description("학원 ID")
                        ),
                        responseFields(
                                fieldWithPath("academyName").type(JsonFieldType.STRING).description("학원 이름"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("학원 연락처"),
                                fieldWithPath("fullAddress").type(JsonFieldType.STRING).description("학원 주소"),
                                fieldWithPath("shuttleAvailability").type(JsonFieldType.STRING).description("학원차 운행 여부"),
                                fieldWithPath("expectedFee").type(JsonFieldType.NUMBER).description("교습비 추정 금액(여러 수업 중 최대)"),
                                fieldWithPath("updatedDate").type(JsonFieldType.STRING).description("학원 데이터 업데이트 날짜"),
                                fieldWithPath("areaOfExpertise").type(JsonFieldType.STRING).description("학원 분야"),
                                fieldWithPath("lessonGetResponses.lessons").type(JsonFieldType.ARRAY).description("학원 수업 정보들"),
                                fieldWithPath("lessonGetResponses.lessons[].lessonId").type(JsonFieldType.NUMBER).description("수업 ID"),
                                fieldWithPath("lessonGetResponses.lessons[].subject").type(JsonFieldType.STRING).description("수업 이름"),
                                fieldWithPath("lessonGetResponses.lessons[].capacity").type(JsonFieldType.NUMBER).description("수업 수용 인원"),
                                fieldWithPath("lessonGetResponses.lessons[].duration").type(JsonFieldType.STRING).description("수업 기간"),
                                fieldWithPath("lessonGetResponses.lessons[].totalFee").type(JsonFieldType.NUMBER).description("수업 총 비용"),
                                fieldWithPath("reviewPercentGetResponse").type(JsonFieldType.OBJECT).description("학원 리뷰 키워드 별 퍼센트"),
                                fieldWithPath("reviewPercentGetResponse.kindnessPercent").type(JsonFieldType.NUMBER).description("선생님이 칠절해요 퍼센트"),
                                fieldWithPath("reviewPercentGetResponse.goodFacilityPercent").type(JsonFieldType.NUMBER).description("시설 좋아요 퍼센트"),
                                fieldWithPath("reviewPercentGetResponse.cheapFeePercent").type(JsonFieldType.NUMBER).description("가격이 저렴해요 퍼센트"),
                                fieldWithPath("reviewPercentGetResponse.goodManagementPercent").type(JsonFieldType.NUMBER).description("시설이 좋아요 퍼센트"),
                                fieldWithPath("reviewPercentGetResponse.lovelyTeachingPercent").type(JsonFieldType.NUMBER).description("학생들에게 애정이 넘쳐오 퍼센트"))
                ))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .log().all()
                .when()
                .get("/academies/{academyId}", savedAcademyAboutSungnam.getId())
                .then();
    }

}
