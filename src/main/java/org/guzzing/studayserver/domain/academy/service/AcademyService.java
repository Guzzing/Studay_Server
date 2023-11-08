package org.guzzing.studayserver.domain.academy.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.Lesson;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.guzzing.studayserver.domain.academy.model.vo.Location;

import org.guzzing.studayserver.domain.academy.repository.academy.AcademyRepository;
import org.guzzing.studayserver.domain.academy.repository.lesson.LessonRepository;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByLocationParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademiesByNameParam;
import org.guzzing.studayserver.domain.academy.service.dto.param.AcademyFilterParam;
import org.guzzing.studayserver.domain.academy.service.dto.result.*;
import org.guzzing.studayserver.domain.academy.util.GeometryUtil;
import org.guzzing.studayserver.domain.academy.util.SqlFormatter;
import org.guzzing.studayserver.domain.academy.util.model.Direction;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyService {

    private static final Double DISTANCE = 2.0;

    private static final int PAGE_SIZE = 5;

    private final AcademyRepository academyRepository;

    private final LessonRepository lessonRepository;

    private final ReviewCountRepository reviewCountRepository;

    public AcademyService(AcademyRepository academyRepository, LessonRepository lessonRepository,
            ReviewCountRepository reviewCountRepository) {
        this.academyRepository = academyRepository;
        this.lessonRepository = lessonRepository;
        this.reviewCountRepository = reviewCountRepository;
    }

    @Transactional(readOnly = true)
    public AcademyGetResult getAcademy(Long academyId) {
        Academy academy = academyRepository.getById(academyId);
        List<Lesson> lessons = lessonRepository.findAllByAcademyId(academyId);
        ReviewCount reviewCount = reviewCountRepository.getByAcademyId(academyId);

        return AcademyGetResult.from(academy, lessons, reviewCount);
    }


    public AcademiesByLocationResults findAcademiesByLocation(AcademiesByLocationParam param) {
        Location northEast = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.NORTHEAST);
        Location southWest = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.SOUTHWEST);
        String diagonal = SqlFormatter.makeDiagonalByLineString(northEast, southWest);

        return AcademiesByLocationResults.to(academyRepository.findAcademiesByLocation(diagonal));
    }

    @Transactional(readOnly = true)
    public AcademiesByNameResults findAcademiesByName(AcademiesByNameParam param) {
        PageRequest requestPageAble = PageRequest.of(param.pageNumber(), PAGE_SIZE);

        return AcademiesByNameResults.to(
                academyRepository.findAcademiesByName(param.academyName(), requestPageAble)
        );
    }

    @Transactional(readOnly = true)
    public AcademyFilterResults filterAcademies(AcademyFilterParam param) {
        Location northEast = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.NORTHEAST);
        Location southWest = calculateLocationWithinRadiusInDirection(
                param.baseLatitude(),
                param.baseLongitude(),
                Direction.SOUTHWEST);
        String diagonal = SqlFormatter.makeDiagonalByLineString(northEast, southWest);

        return AcademyFilterResults.from(academyRepository.filterAcademies(AcademyFilterParam.to(param, diagonal)));
    }

    private Location calculateLocationWithinRadiusInDirection(double latitude, double longitude, Direction direction) {
        return GeometryUtil.calculateLocationWithinRadiusInDirection(latitude, longitude, direction.getBearing(), DISTANCE);
    }

}
