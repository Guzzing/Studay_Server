package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.repository.AcademyFilterCondition;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {

    private final EntityManager em;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<AcademiesByLocation> findAcademiesByLocation(String pointFormat, Long memberId) {

        String nativeQuery = """
        SELECT a.id AS academyId, a.academy_name AS academyName, a.phone_number AS phoneNumber, a.full_address AS fullAddress,
                a.area_of_expertise AS areaOfExpertise, a.latitude AS latitude , a.longitude AS longitude, a.shuttle AS shuttleAvailable,
                (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked
        FROM academies AS a
        LEFT JOIN likes AS l
        ON a.id = l.academy_id AND l.member_id = %s
        WHERE MBRContains(ST_LINESTRINGFROMTEXT(%s, a.point)=1""";

        String formattedQuery = String.format(nativeQuery, memberId, pointFormat);

        Query emNativeQuery = em.createNativeQuery(
                formattedQuery);

        List<AcademiesByLocation> academies = emNativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("areaOfExpertise", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("shuttleAvailable", StandardBasicTypes.STRING)
                .addScalar("isLiked", StandardBasicTypes.BOOLEAN)
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
                                        (Double) tuple[6],
                                        (String) tuple[7],
                                        (boolean) tuple[8]
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

    public List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition, Long memberId) {
        String nativeQuery = """
        SELECT  a.id AS academyId, a.academy_name AS academyName, a.full_address AS fullAddress, 
                a.phone_number AS phoneNumber, a.area_of_expertise AS areaOfExpertise, a.latitude, a.longitude, a.shuttle AS shuttleAvailable, 
                (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked 
        FROM academies AS a 
        LEFT JOIN likes AS l 
        ON a.id = l.academy_id AND l.member_id = %s
        WHERE MBRContains(ST_LINESTRINGFROMTEXT(%s, a.point)=1""";

        String formattedQuery = String.format(nativeQuery, memberId, academyFilterCondition.pointFormat());

        if (academyFilterCondition.areaOfExpertises() != null && !academyFilterCondition.areaOfExpertises().isEmpty()) {
            formattedQuery += " AND area_of_expertise IN " + academyFilterCondition.areaOfExpertises();
        }

        if (academyFilterCondition.desiredMinAmount() != null && academyFilterCondition.desiredMaxAmount() != null) {
            formattedQuery += " AND max_education_fee BETWEEN " + academyFilterCondition.desiredMinAmount() + " AND "
                    + academyFilterCondition.desiredMaxAmount();
        }

        Query query = em.createNativeQuery(formattedQuery);

        return query.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("areaOfExpertise", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("shuttleAvailable", StandardBasicTypes.STRING)
                .addScalar("isLiked", StandardBasicTypes.BOOLEAN)
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
                                        (String) tuple[7],
                                        (boolean) tuple[8]
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
