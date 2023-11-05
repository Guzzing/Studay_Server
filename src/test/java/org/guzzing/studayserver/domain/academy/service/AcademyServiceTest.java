package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByNameParam;
import org.guzzing.studayserver.domain.academy.service.dto.result.*;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles({"oauth","dev"})
@SpringBootTest
class AcademyServiceTest {

    private static final String ACADEMY_NAME_FOR_SEARCH = "코딩";

    @Autowired
    private AcademyService academyService;

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
    @DisplayName("학원 ID로 학원 정보를 조회할 때 학원 정보, 수업 정보, 리뷰를 확인할 수 있다.")
    void getAcademy_academyId_reviewsAndLessons() {
        //When
        AcademyGetResult academyGetResult = academyService.getAcademy(savedAcademyAboutSungnam.getId());

        //Then
        assertThat(academyGetResult.academyName()).isEqualTo(savedAcademyAboutSungnam.getAcademyName());
        assertThat(academyGetResult.contact()).isEqualTo(savedAcademyAboutSungnam.getContact());
        assertThat(academyGetResult.fullAddress()).isEqualTo(savedAcademyAboutSungnam.getFullAddress());
        assertThat(academyGetResult.shuttleAvailability()).isEqualTo(savedAcademyAboutSungnam.getShuttleAvailability().toString());
        assertThat(academyGetResult.expectedFee()).isEqualTo(savedAcademyAboutSungnam.getMaxEducationFee());
        assertThat(academyGetResult.updatedDate()).isEqualTo(savedAcademyAboutSungnam.getUpdatedDate().toString());
        assertThat(academyGetResult.areaOfExpertise()).isEqualTo(savedAcademyAboutSungnam.getAreaOfExpertise());
        assertThat(academyGetResult.lessonGetResults().lessonGetResults()).contains(LessonGetResult.from(savedALessonAboutSungnam));
        assertThat(academyGetResult.reviewPercentGetResult()).isEqualTo(ReviewPercentGetResult.from(savedReviewCountAboutSungnam));
    }

    @Test
    @DisplayName("사용자의 중심 위치가 주어졌을 때 반경 거리 이내의 학원 목록이 조회된다.")
    void findAcademiesByLocation_academiesWithinDistance_equalsSize() {
        //Given
        lessonRepository.deleteAll();
        reviewCountRepository.deleteAll();
        academyRepository.deleteAll();

        double latitude = 37.4449168;
        double longitude = 127.1388684;

        List<Academy> academies = AcademyFixture.randomAcademiesWithinDistance(latitude, longitude);
        for(Academy academy : academies) {
            Academy savedAcademy = academyRepository.save(academy);
            lessonRepository.save(AcademyFixture.lessonForSunganm(savedAcademy));
            reviewCountRepository.save(AcademyFixture.reviewCountDefault(savedAcademy));
        }

        //When
        AcademiesByLocationResults academiesByLocations = academyService.findAcademiesByLocation(AcademyFixture.academiesByLocationParam(latitude,longitude));

        //Then
        assertThat(academiesByLocations.academiesByLocationResults().size()).isEqualTo(academies.size());
    }

    @Test
    @DisplayName("학원 이름(ACADEMY_NAME_FOR_SEARCH)으로 검색하면 자동완성 기능으로 관련 학원들을 보여준다.")
    void findAcademiesByName_academyName_relatedAcademies() {
        //Given
        List<Academy> academies = AcademyFixture.academies();
        for(Academy academy : academies) {
            Academy savedAcademy = academyRepository.save(academy);
            lessonRepository.save(AcademyFixture.lessonForSunganm(savedAcademy));
            reviewCountRepository.save(AcademyFixture.reviewCountDefault(savedAcademy));
        }

        //When
        AcademiesByNameResults academiesByNameResults = academyService.findAcademiesByName(
                AcademiesByNameParam.of(ACADEMY_NAME_FOR_SEARCH, 0)
        );

        //Then
        for(AcademiesByNameResult academiesByNameResult : academiesByNameResults.academiesByNameResults()) {
            assertThat(academiesByNameResult.academyName()).contains(ACADEMY_NAME_FOR_SEARCH);
        }
    }

}
