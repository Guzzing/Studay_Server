package org.guzzing.studayserver.domain.academy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.academycategory.AcademyCategoryRepository;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationWithScrollParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByNameParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterWithScrollParam;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByLocationWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesByNameResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademiesFilterWithScrollResults;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyAndLessonDetailResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyGetResult;
import org.guzzing.studayserver.domain.academy.service.dto.result.LessonInfoToCreateDashboardResults;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyService {

    private static final Double DISTANCE = 2.0;
    private static final int ACADEMY_NAME_SEARCH_PAGE_SIZE = 5;
    private static final int ACADEMY_LOCATION_SEARCH_PAGE_SIZE = 10;
    private final AcademyRepository academyRepository;
    private final LessonRepository lessonRepository;
    private final ReviewCountRepository reviewCountRepository;
    private final AcademyCategoryRepository academyCategoryRepository;

    public AcademyService(AcademyRepository academyRepository, LessonRepository lessonRepository,
                          ReviewCountRepository reviewCountRepository,
                          AcademyCategoryRepository academyCategoryRepository) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.reviewCountRepository = reviewCountRepository;
        this.academyCategoryRepository = academyCategoryRepository;
    }

    @Transactional(readOnly = true)
    public AcademyGetResult getAcademy(Long academyId) {
        return AcademyGetResult.from(
                academyRepository.getById(academyId),
                lessonRepository.findAllByAcademyId(academyId),
                reviewCountRepository.getByAcademyId(academyId),
                academyCategoryRepository.findCategoryIdsByAcademyId(academyId));
    }

    @Transactional(readOnly = true)
    public AcademiesByLocationWithScrollResults findAcademiesByLocationWithScroll(AcademiesByLocationWithScrollParam param) {
        String diagonal = GeometryUtil.makeDiagonal(param.baseLatitude(), param.baseLongitude(), DISTANCE);

        AcademiesByLocationWithScroll academiesByLocation = academyRepository.findAcademiesByLocation(
                diagonal,
                param.memberId(),
                param.pageNumber(),
                ACADEMY_LOCATION_SEARCH_PAGE_SIZE);

        return AcademiesByLocationWithScrollResults.to(
                academiesByLocation);
    }
    
    @Transactional(readOnly = true)
    public AcademiesByNameResults findAcademiesByName(AcademiesByNameParam param) {
        PageRequest requestPageAble = PageRequest.of(param.pageNumber(), ACADEMY_NAME_SEARCH_PAGE_SIZE);

        return AcademiesByNameResults.to(
                academyRepository.findAcademiesByName(param.academyName(), requestPageAble)
        );
    }

    @Transactional(readOnly = true)
    public AcademiesFilterWithScrollResults filterAcademies(AcademyFilterWithScrollParam param, Long memberId) {
        String diagonal = GeometryUtil.makeDiagonal(param.baseLatitude(), param.baseLongitude(), DISTANCE);

        AcademiesByFilterWithScroll academiesByFilterWithScroll = academyRepository.filterAcademies(
                AcademyFilterWithScrollParam.to(param, diagonal), memberId, param.pageNumber(),
                ACADEMY_LOCATION_SEARCH_PAGE_SIZE);

        Map<Long, List<Long>> academyIdWithCategories = makeCategoriesByFilterWithScroll(
                academiesByFilterWithScroll.academiesByLocation());

        return AcademiesFilterWithScrollResults.from(academiesByFilterWithScroll, academyIdWithCategories);
    }

    private Map<Long, List<Long>> makeCategoriesByFilterWithScroll(
            List<AcademyByFilterWithScroll> academiesByFilterWithScroll) {
        Map<Long, List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();
        academiesByFilterWithScroll.forEach(
                academyByFilterWithScroll -> {
                    academyIdWithCategories.put(
                            academyByFilterWithScroll.academyId(),
                            academyCategoryRepository.findCategoryIdsByAcademyId(
                                    academyByFilterWithScroll.academyId()));
                }
        );

        return academyIdWithCategories;
    }

    @Transactional(readOnly = true)
    public LessonInfoToCreateDashboardResults getLessonsInfoAboutAcademy(Long academyId) {

        return LessonInfoToCreateDashboardResults.from(
                lessonRepository.findAllLessonInfoByAcademyId(academyId));
    }

    @Transactional(readOnly = true)
    public AcademyAndLessonDetailResult getAcademyAndLessonDetail(Long lessonId) {
        Lesson lesson = lessonRepository.getLessonById(lessonId);
        List<Long> categoryIds = academyCategoryRepository.findCategoryIdsByAcademyId(lesson.getAcademyId());

        return AcademyAndLessonDetailResult.from(lesson, categoryIds);
    }

}
