package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import org.guzzing.studayserver.domain.academy.repository.dto.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {

    private static final String BLANK_QUERY = "";
    private final EntityManager em;
    private final int PAGE_SIZE = 10;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public AcademyByLocationWithCursorRepositoryResponse findAcademiesByLocationByCursor(
        AcademyByLocationWithCursorRepositoryRequest request) {

        String nativeQuery = """
            SELECT 
                a.id AS academyId,
                a.academy_name AS academyName,
                a.phone_number AS phoneNumber,
                a.full_address AS fullAddress,
                a.latitude AS latitude,
                a.longitude AS longitude,
                a.shuttle AS shuttleAvailable,
                (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked,
                ac.category_id AS categoryId 
            FROM 
                academies AS a
            INNER JOIN
                academy_categories AS ac ON a.id = ac.academy_id    
            LEFT JOIN 
                likes AS l ON a.id = l.academy_id AND l.member_id = %s """;

        String formattedQuery = String.format(nativeQuery, request.memberId());
        formattedQuery += builderWhere();
        formattedQuery += whereWithinDistance(request.pointFormat());
        formattedQuery += makeCursor(request.lastAcademyId());
        formattedQuery += orderByAsc("a.id");
        formattedQuery += limit();

        Query emNativeQuery = em.createNativeQuery(formattedQuery);

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
                .addScalar("categoryId", StandardBasicTypes.LONG)
                .setResultTransformer((tuple, aliases) -> new AcademyByLocationWithScroll(
                    (Long) tuple[0],
                    (String) tuple[1],
                    (String) tuple[2],
                    (String) tuple[3],
                    (Double) tuple[4],
                    (Double) tuple[5],
                    (String) tuple[6],
                    (boolean) tuple[7],
                    (Long) tuple[8]
                ))
                .getResultList();

        return AcademyByLocationWithCursorRepositoryResponse.of(
            academiesByLocation,
            getBeforeLastId(academiesByLocation),
            isHasNest(academiesByLocation.size())
        );
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
                (CASE WHEN l.academy_id IS NOT NULL THEN true ELSE false END) AS isLiked,
                ac.category_id AS categoryId 
            FROM 
                academies AS a
            INNER JOIN
                academy_categories AS ac ON a.id = ac.academy_id    
            LEFT JOIN 
                likes AS l ON a.id = l.academy_id AND l.member_id = %s """;

        String formattedQuery = String.format(nativeQuery, memberId);

        formattedQuery += builderWhere();

        formattedQuery += whereWithinDistance(pointFormat);
        formattedQuery = makeScroll(pageNumber, pageSize, formattedQuery);

        Query emNativeQuery = em.createNativeQuery(formattedQuery);

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
                .addScalar("categoryId", StandardBasicTypes.LONG)
                .setResultTransformer((tuple, aliases) -> new AcademyByLocationWithScroll(
                    (Long) tuple[0],
                    (String) tuple[1],
                    (String) tuple[2],
                    (String) tuple[3],
                    (Double) tuple[4],
                    (Double) tuple[5],
                    (String) tuple[6],
                    (boolean) tuple[7],
                    (Long) tuple[8]
                ))
                .getResultList();

        return AcademiesByLocationWithScroll.of(
            academiesByLocation,
            isHasNest(academiesByLocation.size())
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
        formattedQuery = whereFilters(formattedQuery, academyFilterCondition);
        formattedQuery += orderByDesc("a.id");
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
            isHasNest(academyByFilter.size())
        );
    }

    private String builderWhere() {
        return " WHERE ";

    }

    private boolean isHasNest(int resultSize) {
        return resultSize == PAGE_SIZE;
    }

    private String whereFilters(String formattedQuery, AcademyFilterCondition academyFilterCondition) {
        formattedQuery += whereInCategories(academyFilterCondition);
        formattedQuery += whereBetweenEducationFee(academyFilterCondition);
        return formattedQuery;
    }

    private String whereWithinDistance(String pointFormat) {
        return String.format(" MBRContains(ST_LINESTRINGFROMTEXT(%s), a.point) ", pointFormat);
    }

    private String whereInCategories(AcademyFilterCondition academyFilterCondition) {
        if (academyFilterCondition.categories() != null && !academyFilterCondition.categories().isEmpty()) {
            return " AND ac.category_id IN " + academyFilterCondition.categories();
        }
        return BLANK_QUERY;
    }

    private String whereBetweenEducationFee(AcademyFilterCondition academyFilterCondition) {
        if (academyFilterCondition.desiredMinAmount() != null && academyFilterCondition.desiredMaxAmount() != null) {
            return " AND max_education_fee BETWEEN " + academyFilterCondition.desiredMinAmount() + " AND "
                + academyFilterCondition.desiredMaxAmount();
        }
        return BLANK_QUERY;
    }

    private String makeScroll(int pageNumber, int pageSize, String formattedQuery) {
        formattedQuery += " LIMIT " + pageSize + " OFFSET " + pageNumber * pageSize;
        return formattedQuery;
    }

    private String makeCursor(Long lastAcademyId) {
        return " AND a.id >" + lastAcademyId;
    }

    private String limit() {
        return " LIMIT " + PAGE_SIZE;
    }

    private String orderByDesc(String columnName) {
        return String.format(" ORDER BY %s %s ", columnName, " DESC ");
    }

    private String orderByAsc(String columnName) {
        return String.format(" ORDER BY %s %s ", columnName, " ASC ");
    }

    private Long getBeforeLastId(List<AcademyByLocationWithScroll> academiesByLocation) {
        if (academiesByLocation != null && !academiesByLocation.isEmpty()) {
            return academiesByLocation.get(academiesByLocation.size() - 1).academyId();
        }
        return 0L;
    }

}
