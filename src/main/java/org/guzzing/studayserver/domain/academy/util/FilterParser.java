package org.guzzing.studayserver.domain.academy.util;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.util.dto.DistinctFilteredAcademy;

import java.util.*;

public class FilterParser {

    public static  Map<Long, List<Long>> makeCategories(List<AcademyByFiltering> academiesByFiltering) {
        Map<Long,List<Long>> academyIdWithCategories = new HashMap<>();

        academiesByFiltering.forEach(
                academyByFiltering ->  {
                    List<Long> categories = academyIdWithCategories.getOrDefault(academyByFiltering.academyId(), new ArrayList<>());
                    categories.add(academyByFiltering.categoryId());

                    academyIdWithCategories.put(academyByFiltering.academyId(), categories);
                }
        );

        return academyIdWithCategories;
    }

    public static Set<DistinctFilteredAcademy> distinctFilteredAcademies(List<AcademyByFiltering> academiesByFiltering) {
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
