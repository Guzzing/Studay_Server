package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import org.guzzing.studayserver.domain.academy.repository.dto.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {

    private final EntityManager em;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<AcademiesByLocation> findAcademiesByLocation(String pointFormat, Long memberId) {

        String nativeQuery = """
                SELECT 
                    a.id AS academyId,
                    a.academy_name AS academyName,
                    a.phone_number AS phoneNumber, 
                    a.full_address AS fullAddress,
                    a.latitude AS latitude , 
                    a.longitude AS longitude, 
                    a.shuttle AS shuttleAvailable,
                    (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked,
                     ac.category_id as categoryId
                FROM 
                    academies AS a
                LEFT JOIN 
                    academy_categories as ac ON a.id = ac.academy_id
                LEFT JOIN 
                    likes AS l ON a.id = l.academy_id AND l.member_id = %s
                WHERE 
                    MBRContains(ST_LINESTRINGFROMTEXT(%s), a.point)""";

        String formattedQuery = String.format(nativeQuery, memberId, pointFormat);

        Query emNativeQuery = em.createNativeQuery(
                formattedQuery);

        List<AcademiesByLocation> academies = emNativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("shuttleAvailable", StandardBasicTypes.STRING)
                .addScalar("isLiked", StandardBasicTypes.BOOLEAN)
                .addScalar("categoryId", StandardBasicTypes.LONG)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(Object[] tuple, String[] aliases) {
                                return new AcademiesByLocation(
                                        (Long) tuple[0],
                                        (String) tuple[1],
                                        (String) tuple[2],
                                        (String) tuple[3],
                                        (Double) tuple[4],
                                        (Double) tuple[5],
                                        (String) tuple[6],
                                        (boolean) tuple[7],
                                        (Long) tuple[8]
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
                SELECT  
                    a.id AS academyId, 
                    a.academy_name AS academyName, 
                    a.full_address AS fullAddress, 
                    a.phone_number AS phoneNumber,
                    a.latitude, 
                    a.longitude, 
                    a.shuttle AS shuttleAvailable, 
                    ac.category_id as categoryId,
                    (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked 
                FROM 
                    academy_categories as ac
                LEFT JOIN 
                    academies AS a ON ac.academy_id = a.id 
                LEFT JOIN 
                    likes AS l ON a.id = l.academy_id AND l.member_id = %s
                WHERE 
                    MBRContains(ST_LINESTRINGFROMTEXT(%s), a.point)""";

        String formattedQuery = String.format(nativeQuery, memberId, academyFilterCondition.pointFormat());
        formattedQuery = addWhereConditionsWithFilter(formattedQuery, academyFilterCondition);

        Query query = em.createNativeQuery(formattedQuery);

        return query.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("categoryId", StandardBasicTypes.LONG)
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
                                        (Long) tuple[4],
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

    public AcademiesByLocationWithScroll findAcademiesByLocation(
            String pointFormat,
            Long memberId,
            int pageNumber,
            int pageSize) {

        String nativeQuery = """
                SELECT 
                    a.id AS academyId,
                    a.academy_name AS academyName,
                    a.phone_number AS phoneNumber,
                    a.full_address AS fullAddress,
                    a.latitude AS latitude,
                    a.longitude AS longitude,
                    a.shuttle AS shuttleAvailable,
                    (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked
                FROM 
                    academies AS a
                LEFT JOIN 
                    likes AS l ON a.id = l.academy_id AND l.member_id = %s
                WHERE 
                    MBRContains(ST_LINESTRINGFROMTEXT(%s), a.point) """;

        String formattedQuery = String.format(nativeQuery, memberId, pointFormat);
        formattedQuery += orderBy("a.academy_name");
        formattedQuery = makeScroll(pageNumber, pageSize, formattedQuery);

        Query emNativeQuery = em.createNativeQuery(
                formattedQuery);

        List<AcademyByLocationWithScroll> academiesByLocation =
                emNativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                        .addScalar("academyId", StandardBasicTypes.LONG)
                        .addScalar("academyName", StandardBasicTypes.STRING)
                        .addScalar("fullAddress", StandardBasicTypes.STRING)
                        .addScalar("phoneNumber", StandardBasicTypes.STRING)
                        .addScalar("latitude", StandardBasicTypes.DOUBLE)
                        .addScalar("longitude", StandardBasicTypes.DOUBLE)
                        .addScalar("shuttleAvailable", StandardBasicTypes.STRING)
                        .addScalar("isLiked", StandardBasicTypes.BOOLEAN)
                        .setResultTransformer((tuple, aliases) -> new AcademyByLocationWithScroll(
                                (Long) tuple[0],
                                (String) tuple[1],
                                (String) tuple[2],
                                (String) tuple[3],
                                (Double) tuple[4],
                                (Double) tuple[5],
                                (String) tuple[6],
                                (boolean) tuple[7]
                        ))
                        .getResultList();

        return AcademiesByLocationWithScroll.of(
                academiesByLocation,
                isHasNest(academiesByLocation.size(), pageSize)
        );
    }

    public AcademiesByFilterWithScroll filterAcademies(
            AcademyFilterCondition academyFilterCondition,
            Long memberId,
            int pageNumber,
            int pageSize) {
        String nativeQuery = """
                SELECT  DISTINCT
                    a.id AS academyId, 
                    a.academy_name AS academyName, 
                    a.full_address AS fullAddress, 
                    a.phone_number AS phoneNumber, 
                    a.latitude, a.longitude, 
                    a.shuttle AS shuttleAvailable, 
                    (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked 
                FROM 
                    academy_categories as ac
                LEFT JOIN 
                    academies AS a ON ac.academy_id = a.id 
                LEFT JOIN 
                    likes AS l ON a.id = l.academy_id AND l.member_id = %s
                WHERE 
                    MBRContains(ST_LINESTRINGFROMTEXT(%s), a.point)
                """;


        String formattedQuery = String.format(
                nativeQuery,
                memberId,
                academyFilterCondition.pointFormat());
        formattedQuery = addWhereConditionsWithFilter(formattedQuery, academyFilterCondition);
        formattedQuery += orderBy("a.academy_name");
        formattedQuery = makeScroll(pageNumber, pageSize, formattedQuery);

        Query query = em.createNativeQuery(formattedQuery);

        List<AcademyByFilterWithScroll> academyByFilter = query.unwrap(NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("shuttleAvailable", StandardBasicTypes.STRING)
                .addScalar("isLiked", StandardBasicTypes.BOOLEAN)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(Object[] tuple, String[] aliases) {
                                return new AcademyByFilterWithScroll(
                                        (Long) tuple[0],
                                        (String) tuple[1],
                                        (String) tuple[2],
                                        (String) tuple[3],
                                        (Double) tuple[4],
                                        (Double) tuple[5],
                                        (String) tuple[6],
                                        (boolean) tuple[7]
                                );
                            }

                            @Override
                            public List transformList(List collection) {
                                return collection;
                            }
                        }
                )
                .getResultList();

        return AcademiesByFilterWithScroll.of(
                academyByFilter,
                isHasNest(academyByFilter.size(), pageSize)
        );
    }

    private boolean isHasNest(int resultSize, int pageSize) {
        return resultSize == pageSize;
    }

    private String addWhereConditionsWithFilter(String formattedQuery, AcademyFilterCondition academyFilterCondition) {
        formattedQuery += whereInCategories(academyFilterCondition);
        formattedQuery += whereBetweenEducationFee(academyFilterCondition);
        return formattedQuery;
    }

    private String whereInCategories(AcademyFilterCondition academyFilterCondition) {
        if (academyFilterCondition.categories() != null && !academyFilterCondition.categories().isEmpty()) {
            return " AND ac.category_id IN " + academyFilterCondition.categories();
        }
        return "";
    }

    private String whereBetweenEducationFee(AcademyFilterCondition academyFilterCondition) {
        if (academyFilterCondition.desiredMinAmount() != null && academyFilterCondition.desiredMaxAmount() != null) {
            return " AND max_education_fee BETWEEN " + academyFilterCondition.desiredMinAmount() + " AND "
                    + academyFilterCondition.desiredMaxAmount();
        }
        return "";
    }

    private String makeScroll(int pageNumber, int pageSize, String formattedQuery) {
        int offset = pageNumber * pageSize;
        formattedQuery += " LIMIT " + pageSize + " OFFSET " + offset;
        return formattedQuery;
    }

    private String orderBy(String columnName) {
        return " ORDER BY "+columnName;
    }

}
