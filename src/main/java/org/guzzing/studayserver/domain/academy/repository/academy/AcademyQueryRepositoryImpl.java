package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.repository.AcademyFilterCondition;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;


import java.util.List;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {
    private final EntityManager em;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<AcademiesByLocation> findAcademiesByLocation(String pointFormat) {

        Query query = em.createNativeQuery(
                "SELECT a.id AS academyId, a.academy_name AS academyName, a.phone_number AS phoneNumber, a.full_address AS fullAddress," +
                        " a.area_of_expertise AS areaOfExpertise, a.latitude AS latitude , a.longitude AS longitude FROM academies AS a " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", a.point)=1");

        List<AcademiesByLocation> academies = query.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("areaOfExpertise", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(Object[] tuple, String[] aliases) {
                                return new AcademiesByLocation(
                                        (Long) tuple[0],
                                        (String) tuple[1],
                                        (String) tuple[2],
                                        (String) tuple[3],
                                        (String) tuple[4],
                                        (Double) tuple[5],
                                        (Double) tuple[6]
                                );
                            }

                            @Override
                            public List transformList(List collection) {
                                return collection;
                            }
                        }
                )
                .getResultList();

        return academies;
    }

    public List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition) {
        String nativeQuery = "SELECT  a.id AS academyId, a.academy_name AS academyName, a.full_address AS fullAddress, " +
                "a.phone_number AS phoneNumber, a.area_of_expertise AS areaOfExpertise, a.latitude, a.longitude, a.shuttle AS shuttleAvailable " +
                "FROM academies AS a " +
                "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + academyFilterCondition.pointFormat() + ", a.point)=1 ";

        if (academyFilterCondition.areaOfExpertises() != null && !academyFilterCondition.areaOfExpertises().isEmpty()) {
            nativeQuery += " AND area_of_expertise IN "+ academyFilterCondition.areaOfExpertises();
        }

        if (academyFilterCondition.desiredMinAmount() != null && academyFilterCondition.desiredMaxAmount() != null) {
            nativeQuery += " AND max_education_fee BETWEEN "+academyFilterCondition.desiredMinAmount() +" AND "+academyFilterCondition.desiredMaxAmount();
        }

        Query query = em.createNativeQuery(nativeQuery);

        return query.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("areaOfExpertise", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("shuttleAvailable", StandardBasicTypes.STRING)  // 추가된 부분
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(Object[] tuple, String[] aliases) {
                                return new AcademyByFiltering(
                                        (Long) tuple[0],
                                        (String) tuple[1],
                                        (String) tuple[2],
                                        (String) tuple[3],
                                        (String) tuple[4],
                                        (Double) tuple[5],
                                        (Double) tuple[6],
                                        (String) tuple[7]  // shuttleAvailable 추가된 부분
                                );
                            }

                            @Override
                            public List transformList(List collection) {
                                return collection;
                            }
                        }
                )
                .getResultList();
    }

}
