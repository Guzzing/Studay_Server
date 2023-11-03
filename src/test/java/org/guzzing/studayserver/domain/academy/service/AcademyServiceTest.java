package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.AcademyGetResult;
import org.guzzing.studayserver.domain.academy.service.dto.LessonGetResult;
import org.guzzing.studayserver.domain.academy.service.dto.ReviewPercentGetResult;
import org.guzzing.studayserver.testutil.fixture.AcademyFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles({"oauth","dev"})
@SpringBootTest
class AcademyServiceTest {

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
    void getAcademy() {
        //Given
        AcademyGetResult academyGetResult = academyService.getAcademy(savedAcademyAboutSungnam.getId());

        //When
        assertThat(academyGetResult.academyName()).isEqualTo(savedAcademyAboutSungnam.getName());
        assertThat(academyGetResult.contact()).isEqualTo(savedAcademyAboutSungnam.getContact());
        assertThat(academyGetResult.fullAddress()).isEqualTo(savedAcademyAboutSungnam.getAddress());
        assertThat(academyGetResult.shuttleAvailability()).isEqualTo(savedAcademyAboutSungnam.getShuttleAvailability().toString());
        assertThat(academyGetResult.expectedFee()).isEqualTo(savedAcademyAboutSungnam.getMaxEducationFee());
        assertThat(academyGetResult.updatedDate()).isEqualTo(savedAcademyAboutSungnam.getUpdatedDate().toString());
        assertThat(academyGetResult.areaOfExpertise()).isEqualTo(savedAcademyAboutSungnam.getAreaOfExpertise());
        assertThat(academyGetResult.lessonGetResults().lessonGetResults()).contains(LessonGetResult.from(savedALessonAboutSungnam));
        assertThat(academyGetResult.reviewPercentGetResult()).isEqualTo(ReviewPercentGetResult.from(savedReviewCountAboutSungnam));
    }

}
