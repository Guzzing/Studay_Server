package org.guzzing.studayserver.domain.academy.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.dto.LessonInfoToCreateDashboard;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByNameParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterParam;
import org.guzzing.studayserver.domain.academy.service.dto.result.*;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;
import org.guzzing.studayserver.domain.academy.util.SqlFormatter;
import org.guzzing.studayserver.domain.academy.util.model.Direction;
import org.guzzing.studayserver.domain.like.service.LikeAccessService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AcademyService {

    private static final Double DISTANCE = 2.0;

    private static final int PAGE_SIZE = 5;

    private final AcademyRepository academyRepository;

    private final LessonRepository lessonRepository;

    private final ReviewCountRepository reviewCountRepository;

    private final LikeAccessService likeAccessService;

    public AcademyService(AcademyRepository academyRepository, LessonRepository lessonRepository,
            ReviewCountRepository reviewCountRepository, LikeAccessService likeAccessService) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.reviewCountRepository = reviewCountRepository;
        this.likeAccessService = likeAccessService;
    }

    //캐시 이용하기(지금 상황에서는 백오피스가 없기 때문에 3달에 한 번 업데이트 되기 때문에 가능)
    @Transactional(readOnly = true)
    public AcademyGetResult getAcademy(Long academyId, Long memberId) {
        return AcademyGetResult.from(
                academyRepository.getById(academyId),
                lessonRepository.findAllByAcademyId(academyId),
                reviewCountRepository.getByAcademyId(academyId),
                isLiked(academyId, memberId));
    }

    @Transactional(readOnly = true)
    public AcademiesByLocationResults findAcademiesByLocation(AcademiesByLocationParam param, Long memberId) {
        Location northEast = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.NORTHEAST);
        Location southWest = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.SOUTHWEST);
        String diagonal = SqlFormatter.makeDiagonalByLineString(northEast, southWest);

        return AcademiesByLocationResults.to(academyRepository.findAcademiesByLocation(diagonal, memberId));
    }

    @Transactional(readOnly = true)
    public AcademiesByNameResults findAcademiesByName(AcademiesByNameParam param) {
        PageRequest requestPageAble = PageRequest.of(param.pageNumber(), PAGE_SIZE);

        return AcademiesByNameResults.to(
                academyRepository.findAcademiesByName(param.academyName(), requestPageAble)
        );
    }

    @Transactional(readOnly = true)
    public AcademyFilterResults filterAcademies(AcademyFilterParam param, Long memberId) {
        Location northEast = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.NORTHEAST);
        Location southWest = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.SOUTHWEST);
        String diagonal = SqlFormatter.makeDiagonalByLineString(northEast, southWest);

        return AcademyFilterResults.from(
                academyRepository.filterAcademies(
                        AcademyFilterParam.to(
                                param,
                                diagonal),
                        memberId)
        );
    }

    private Location calculateLocationWithinRadiusInDirection(
            double latitude,
            double longitude,
            Direction direction) {

        return GeometryUtil.calculateLocationWithinRadiusInDirection(
                latitude,
                longitude,
                direction.getBearing(),
                DISTANCE);
    }

    private boolean isLiked(Long academyId, Long memberId) {
        return likeAccessService.isLiked(academyId, memberId);
    }

    @Transactional(readOnly = true)
    public LessonInfoToCreateDashboardResults getLessonsInfoAboutAcademy(Long academyId) {
        academyRepository.getById(academyId);

        return LessonInfoToCreateDashboardResults.from(lessonRepository.findAllLessonInfoByAcademyId(academyId));
    }

}
