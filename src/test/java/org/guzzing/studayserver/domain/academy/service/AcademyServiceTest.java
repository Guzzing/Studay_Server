package org.guzzing.studayserver.domain.academy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;
import java.util.Random;
import javax.sql.DataSource;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.academycategory.AcademyCategoryRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByNameParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterWithScrollParam;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesFilterWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyGetResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonGetResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonInfoToCreateDashboardResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.ReviewPercentGetResult;
import org.guzzing.studayserver.domain.academy.util.CategoryInfo;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Import(TestDatabaseConfig.class)
@SpringBootTest(webEnvironment = NONE)
class AcademyServiceTest {

    private static final String ACADEMY_NAME_FOR_SEARCH = "코딩";
    private static final int LOCATION_PAGE_SIZE = 10;
    private static final double LATITUDE = 37.4449168;
    private static final double LONGITUDE = 127.1388684;

    @Autowired
    private AcademyService academyService;

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ReviewCountRepository reviewCountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AcademyCategoryRepository academyCategoryRepository;

    private Member savedMember;

    private Academy savedAcademyAboutSungnam;

    private Lesson savedALessonAboutSungnam;

    private ReviewCount savedReviewCountAboutSungnam;

    @Autowired
    private DataSource dataSource;

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
    }

    @Test
    @DisplayName("학원 ID로 학원 정보를 조회할 때 학원 정보, 수업 정보, 리뷰를 확인할 수 있다.")
    void getAcademy_academyId_reviewsAndLessons() {
        //Given
        List<Long> savedCategoryIds = academyCategoryRepository.findCategoryIdsByAcademyId(
                savedAcademyAboutSungnam.getId());

        //When
        AcademyGetResult academyGetResult = academyService.getAcademy(savedAcademyAboutSungnam.getId());

        //Then
        assertThat(academyGetResult.academyName()).isEqualTo(savedAcademyAboutSungnam.getAcademyName());
        assertThat(academyGetResult.contact()).isEqualTo(savedAcademyAboutSungnam.getContact());
        assertThat(academyGetResult.fullAddress()).isEqualTo(savedAcademyAboutSungnam.getFullAddress());
        assertThat(academyGetResult.shuttleAvailability()).isEqualTo(
                savedAcademyAboutSungnam.getShuttleAvailability().toString());
        assertThat(academyGetResult.expectedFee()).isEqualTo(savedAcademyAboutSungnam.getMaxEducationFee());
        assertThat(academyGetResult.updatedDate()).isEqualTo(savedAcademyAboutSungnam.getUpdatedDate().toString());
        savedCategoryIds.forEach(
                savedCategoryId -> assertThat(academyGetResult.categories()).contains(
                        CategoryInfo.getCategoryNameById(savedCategoryId))
        );
        assertThat(academyGetResult.lessonGetResults().lessonGetResults()).contains(
                LessonGetResult.from(savedALessonAboutSungnam));
        assertThat(academyGetResult.reviewPercentGetResult()).isEqualTo(
                ReviewPercentGetResult.from(savedReviewCountAboutSungnam));
    }

    @Test
    @DisplayName("사용자의 중심 위치가 주어졌을 때 반경 거리 이내의 학원 목록이 " + LOCATION_PAGE_SIZE + " 이내로 조회된다.")
    void findAcademiesByLocation_academiesWithinDistance_equalsPageSize() {
        //Given
        lessonRepository.deleteAll();
        reviewCountRepository.deleteAll();
        academyCategoryRepository.deleteAll();
        academyRepository.deleteAll();

        List<Academy> academies = AcademyFixture.randomAcademiesWithinDistance(LATITUDE, LONGITUDE);
        for (Academy academy : academies) {
            Academy savedAcademy = academyRepository.save(academy);
            lessonRepository.save(AcademyFixture.lessonForSunganm(savedAcademy));
            reviewCountRepository.save(AcademyFixture.reviewCountDefault(savedAcademy));

            AcademyFixture.academyCategoryAboutSungnam(savedAcademy)
                    .forEach(
                            academyCategory -> academyCategoryRepository.save(academyCategory)
                    );
        }
        List<String> categoryNames = AcademyFixture.academyCategoryAboutSungnam(savedAcademyAboutSungnam).stream()
                .map(academyCategory -> academyCategory.getCategoryId())
                .map(categoryId -> CategoryInfo.getCategoryNameById(categoryId))
                .toList();

        int totalSize = academies.size();
        int expectedSearchedPageSize = totalSize >= LOCATION_PAGE_SIZE ? LOCATION_PAGE_SIZE : totalSize;
        boolean expectedHasNext = totalSize >= LOCATION_PAGE_SIZE ? true : false;

        //When
        AcademiesByLocationWithScrollResults academiesByLocationWithScroll = academyService.findAcademiesByLocationWithScroll(
                AcademyFixture.academiesByLocationWithScrollParam(LATITUDE, LONGITUDE));

        //Then
        assertThat(academiesByLocationWithScroll.academiesByLocationResults().size()).isEqualTo(
                expectedSearchedPageSize);
        assertThat(academiesByLocationWithScroll.hasNext()).isEqualTo(expectedHasNext);
        academiesByLocationWithScroll.academiesByLocationResults()
                .stream()
                .map(academiesByLocationResult -> academiesByLocationResult.categories())
                .forEach(
                        categoryName -> assertThat(categoryNames).isEqualTo(categoryName)
                );
    }

    @Test
    @DisplayName("학원 이름(ACADEMY_NAME_FOR_SEARCH)으로 검색하면 자동완성 기능으로 관련 학원들을 보여준다.")
    void findAcademiesByName_academyName_relatedAcademies() {
        //Given
        List<Academy> academies = AcademyFixture.academies();
        for (Academy academy : academies) {
            Academy savedAcademy = academyRepository.save(academy);
            lessonRepository.save(AcademyFixture.lessonForSunganm(savedAcademy));
            reviewCountRepository.save(AcademyFixture.reviewCountDefault(savedAcademy));
        }

        //When
        AcademiesByNameResults academiesByNameResults = academyService.findAcademiesByName(
                AcademiesByNameParam.of(ACADEMY_NAME_FOR_SEARCH, 0)
        );

        //Then
        for (AcademiesByNameResult academiesByNameResult : academiesByNameResults.academiesByNameResults()) {
            assertThat(academiesByNameResult.academyName()).contains(ACADEMY_NAME_FOR_SEARCH);
        }
    }

    @Test
    @DisplayName("중심 위치 반경 이내에 있는 학원 중에서 교육비가 최소와 최대 사이에 있고 선택한 학원 분류 분야에 해당하는 학원들을 반환한다." +
            "또한 학원 목록 조회 결과가 " + LOCATION_PAGE_SIZE + " 이내로 조회된다.")
    void filterAcademy_BetweenEducationFeeAndLocationAndInExpertise_withScroll() {
        //Given
        Academy savedAcademyWithTwoCategories = academySetUpForFilterAndDetail().academy;

        List<Academy> academies = AcademyFixture.randomAcademiesWithinDistance(LATITUDE, LONGITUDE);
        Long minFee = 10000L;
        Long maxFee = 1000000L;

        for (Academy academy : academies) {
            Academy savedAcademy = academyRepository.save(academy);
            savedAcademy.changeEducationFee(generateRandomAmount(minFee, maxFee));

            AcademyFixture.academyCategoryAboutTwoCategories(savedAcademy)
                    .forEach(
                            academyCategory -> academyCategoryRepository.save(academyCategory)
                    );

            lessonRepository.save(AcademyFixture.lessonForSunganm(savedAcademy));
            reviewCountRepository.save(AcademyFixture.reviewCountDefault(savedAcademy));
        }
        AcademyFilterWithScrollParam academyFilterWithScrollParam = AcademyFixture.academyFilterWithScrollParam(
                LATITUDE, LONGITUDE, minFee, maxFee);

        int totalSize = academies.size();
        int expectedSearchedPageSize = totalSize >= LOCATION_PAGE_SIZE ? LOCATION_PAGE_SIZE : totalSize;
        boolean expectedHasNext = totalSize >= LOCATION_PAGE_SIZE ? true : false;

        //When
        AcademiesFilterWithScrollResults academiesFilterWithScrollResults = academyService.filterAcademies(
                academyFilterWithScrollParam,
                savedAcademyWithTwoCategories.getId());

        //Then
        for (AcademiesFilterWithScrollResults.AcademyFilterWithScrollResult academyFilterResult : academiesFilterWithScrollResults.academiesFilterWithScrollResults()) {
            Academy filtedAcademy = academyRepository.getById(academyFilterResult.academyId());

            assertThat(filtedAcademy.getMaxEducationFee()).
                    isGreaterThanOrEqualTo(minFee)
                    .isLessThanOrEqualTo(maxFee);
            academyFilterResult.categories()
                    .forEach(
                            result -> assertThat(academyFilterWithScrollParam.categories()).contains(result)
                    );
        }
        assertThat(academiesFilterWithScrollResults.academiesFilterWithScrollResults().size()).isEqualTo(
                expectedSearchedPageSize);
        assertThat(academiesFilterWithScrollResults.hasNext()).isEqualTo(expectedHasNext);
    }

    private long generateRandomAmount(long min, long max) {
        if (min >= max) {
            throw new IllegalArgumentException("최소값은 최대값과 같거나 클 수 없습니다.");
        }
        Random random = new Random();
        return min + random.nextInt((int) (max - min + 1));
    }

    @Test
    @DisplayName("스케줄 상세보기 때 필요한 학원 정보를 올바르게 불러오는지 확인한다.")
    void getAcademyAndLessonDetail_success() {
        //Given
        SavedAcademyAndLesson savedAcademyAndLesson = academySetUpForFilterAndDetail();
        Academy savedAcademy = savedAcademyAndLesson.academy;
        Lesson savedLesson = savedAcademyAndLesson.lesson;
        List<Long> categoriesIds = academyCategoryRepository.findCategoryIdsByAcademyId(savedAcademy.getId());

        //When
        AcademyAndLessonDetailResult academyAndLessonDetail = academyService.getAcademyAndLessonDetail(
                savedLesson.getId());

        //Then
        assertThat(academyAndLessonDetail.academyName()).isEqualTo(savedAcademy.getAcademyName());
        assertThat(academyAndLessonDetail.address()).isEqualTo(savedAcademy.getFullAddress());
        assertThat(academyAndLessonDetail.lessonName()).isEqualTo(savedLesson.getCurriculum());
        assertThat(academyAndLessonDetail.capacity()).isEqualTo(savedLesson.getCapacity());
        assertThat(academyAndLessonDetail.totalFee()).isEqualTo(savedLesson.getTotalFee());
        categoriesIds.forEach(
                categoriesId -> assertThat(academyAndLessonDetail.categories())
                        .contains(CategoryInfo.getCategoryNameById(categoriesId))
        );
    }

    @Test
    @DisplayName("학원 ID로 학원을 검색했을 때 진행하는 수업의 과목과 ID를 올바르게 반환한다.")
    void getLessonInfosAboutAcademy() {
        //When
        LessonInfoToCreateDashboardResults lessonsInfoAboutAcademy
                = academyService.getLessonsInfoAboutAcademy(savedAcademyAboutSungnam.getId());

        //Then
        lessonsInfoAboutAcademy.lessonInfoToCreateDashboardResults()
                .forEach(
                        lessonInfoToCreateDashboardResult -> {
                            assertThat(lessonInfoToCreateDashboardResult.lessonId()).isEqualTo(
                                    savedALessonAboutSungnam.getId());
                            assertThat(lessonInfoToCreateDashboardResult.subject()).isEqualTo(
                                    savedALessonAboutSungnam.getCurriculum());
                        }
                );

    }

    private SavedAcademyAndLesson academySetUpForFilterAndDetail() {
        lessonRepository.deleteAll();
        reviewCountRepository.deleteAll();
        academyCategoryRepository.deleteAll();
        academyRepository.deleteAll();

        Academy academyWithTwoCategories = AcademyFixture.twoCategoriesAcademy();
        Academy savedAcademyWithTwoCategories = academyRepository.save(academyWithTwoCategories);

        AcademyFixture.academyCategoryAboutTwoCategories(savedAcademyWithTwoCategories)
                .forEach(
                        academyCategory -> academyCategoryRepository.save(academyCategory)
                );

        Lesson lesson = AcademyFixture.twoCategoriesLessonFor(savedAcademyWithTwoCategories);
        Lesson savedALesson = lessonRepository.save(lesson);

        ReviewCount savedReviewCount = reviewCountRepository.save(
                AcademyFixture.reviewCountDefault(savedAcademyWithTwoCategories));

        return new SavedAcademyAndLesson(savedAcademyWithTwoCategories, savedALesson);
    }

    record SavedAcademyAndLesson(
            Academy academy,
            Lesson lesson
    ) {

    }

}
