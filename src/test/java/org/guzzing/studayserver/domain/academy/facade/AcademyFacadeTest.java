package org.guzzing.studayserver.domain.academy.facade;

import jakarta.transaction.Transactional;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeParam;
import org.guzzing.studayserver.domain.academy.facade.dto.AcademyDetailFacadeResult;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.academycategory.AcademyCategoryRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.TestDatabaseConfig;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.testutil.fixture.academy.AcademyFixture;
import org.guzzing.studayserver.testutil.fixture.member.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
@Import(TestDatabaseConfig.class)
public class AcademyFacadeTest {

    @Autowired
    private AcademyFacade academyFacade;

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AcademyCategoryRepository academyCategoryRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ReviewCountRepository reviewCountRepository;


    private Member savedMember;
    private Academy savedAcademyAboutSungnam;
    private Lesson savedALessonAboutSungnam;
    private ReviewCount savedReviewCountAboutSungnam;

    @BeforeEach
    void setUp() {

        Member member = MemberFixture.member();
        savedMember = memberRepository.save(member);

        Academy academyAboutSungnam = AcademyFixture.academySungnam();
        academyAboutSungnam.changeEducationFee(100000L);
        savedAcademyAboutSungnam = academyRepository.save(academyAboutSungnam);

        AcademyFixture.academyCategoryAboutSungnam(savedAcademyAboutSungnam)
                .forEach(
                        academyCategory -> academyCategoryRepository.save(academyCategory)
                );

        Lesson lessonAboutSungnam = AcademyFixture.lessonForSunganm(savedAcademyAboutSungnam);
        savedALessonAboutSungnam = lessonRepository.save(lessonAboutSungnam);

        savedReviewCountAboutSungnam = reviewCountRepository.save(
                AcademyFixture.reviewCountDefault(savedAcademyAboutSungnam));

        likeRepository.save(Like.of(savedMember, savedAcademyAboutSungnam));
    }

    @Test
    @DisplayName("학원 상세보기를 하면 해당 게시물의 좋아요 상태와 상세 정보를 확인할 수 있다.")
    void getDetailAcademy() {
        //Given
        AcademyDetailFacadeParam param = AcademyFixture.academyDetailFacadeParam(
                savedMember.getId(),
                savedAcademyAboutSungnam.getId()
        );

        //When
        AcademyDetailFacadeResult detailAcademy = academyFacade.getDetailAcademy(param);

        //Then
        assertAll("Academy Details",
                () -> assertThat(detailAcademy.academyName()).isEqualTo(savedAcademyAboutSungnam.getAcademyName()),
                () -> assertThat(detailAcademy.isLiked()).isEqualTo(true),
                () -> assertThat(detailAcademy.contact()).isEqualTo(savedAcademyAboutSungnam.getContact()),
                () -> assertThat(detailAcademy.expectedFee()).isEqualTo(savedAcademyAboutSungnam.getMaxEducationFee()),
                () -> assertThat(detailAcademy.fullAddress()).isEqualTo(savedAcademyAboutSungnam.getFullAddress()),
                () -> assertThat(detailAcademy.shuttleAvailability()).isEqualTo(savedAcademyAboutSungnam.getShuttleAvailability())
        );
    }

}
