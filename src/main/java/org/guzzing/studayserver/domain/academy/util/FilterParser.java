package org.guzzing.studayserver.domain.academy.util;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.util.dto.DistinctFilteredAcademy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FilterParser {

    public static  Map<Long, List<Long>> makeCategoriesWithFilter(List<AcademyByFiltering> academiesByFiltering) {
        Map<Long,List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByFiltering.forEach(
                academyByFiltering ->  {
                    List<Long> categories = academyIdWithCategories.computeIfAbsent(academyByFiltering.academyId(),c->new ArrayList<>());
                    categories.add(academyByFiltering.categoryId());

                    academyIdWithCategories.put(academyByFiltering.academyId(), categories);
                }
        );

        return academyIdWithCategories;
    }

    public static  Map<Long, List<Long>> makeCategoriesWithLocation(List<AcademiesByLocation> academiesByLocations) {
        Map<Long,List<Long>> academyIdWithCategories = new ConcurrentHashMap<>();

        academiesByLocations.forEach(
                academyByFiltering ->  {
                    List<Long> categories = academyIdWithCategories.computeIfAbsent(academyByFiltering.academyId(), c->new ArrayList<>());
                    categories.add(academyByFiltering.categoryId());

                    academyIdWithCategories.put(academyByFiltering.academyId(), categories);
                }
        );

        return academyIdWithCategories;
    }


    public static Set<DistinctFilteredAcademy> distinctAcademiesWithLocation(List<AcademiesByLocation> academiesByLocations) {
        Set<DistinctFilteredAcademy> distinctFilteredAcademies = new HashSet<>();
        academiesByLocations.forEach(
                academyByFiltering -> distinctFilteredAcademies.add(
                        new DistinctFilteredAcademy(
                                academyByFiltering.academyId(),
                                academyByFiltering.academyName(),
                                academyByFiltering.fullAddress(),
                                academyByFiltering.phoneNumber(),
                                academyByFiltering.latitude(),
                                academyByFiltering.longitude(),
                                academyByFiltering.shuttleAvailable(),
                                academyByFiltering.isLiked()
                        )
                )
        );

        return distinctFilteredAcademies;
    }


    public static Set<DistinctFilteredAcademy> distinctAcademiesWithFilter(List<AcademyByFiltering> academiesByFiltering) {
        Set<DistinctFilteredAcademy> distinctFilteredAcademies = new HashSet<>();
        academiesByFiltering.forEach(
               academyByFiltering -> distinctFilteredAcademies.add(
                       new DistinctFilteredAcademy(
                               academyByFiltering.academyId(),
                               academyByFiltering.academyName(),
                               academyByFiltering.fullAddress(),
                               academyByFiltering.phoneNumber(),
                               academyByFiltering.latitude(),
                               academyByFiltering.longitude(),
                               academyByFiltering.shuttleAvailable(),
                               academyByFiltering.isLiked()
                       )
               )
        );

        return distinctFilteredAcademies;
    }

}
