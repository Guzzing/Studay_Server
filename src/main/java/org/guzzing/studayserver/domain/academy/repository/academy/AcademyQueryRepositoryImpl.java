package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import org.guzzing.studayserver.domain.academy.repository.dto.*;
import org.guzzing.studayserver.domain.academy.repository.dto.request.AcademyByLocationWithCursorRepositoryRequest;
import org.guzzing.studayserver.domain.academy.repository.dto.response.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;

import static org.guzzing.studayserver.domain.academy.repository.academy.SqlQueryMaker.*;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {

    private final EntityManager em;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public AcademyByLocationWithCursorNotLikeRepositoryResponse findAcademiesByCursorAndNotLike(
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
                ac.category_id AS categoryId
            FROM
                academies AS a
            INNER JOIN
                academy_categories AS ac ON a.id = ac.academy_id
                """;

        String formattedQuery = nativeQuery;
        formattedQuery += builderWhere();
        formattedQuery += whereWithinDistance(request.pointFormat());
        formattedQuery += makeCursor(request.lastAcademyId());
        formattedQuery += orderByAsc("a.id");
        formattedQuery += limit();

        Query emNativeQuery = em.createNativeQuery(formattedQuery);

        List<AcademyByLocationWithNotLikeRepositoryResponse> academiesByLocation =
            emNativeQuery.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("fullAddress", StandardBasicTypes.STRING)
                .addScalar("phoneNumber", StandardBasicTypes.STRING)
                .addScalar("latitude", StandardBasicTypes.DOUBLE)
                .addScalar("longitude", StandardBasicTypes.DOUBLE)
                .addScalar("shuttleAvailable", StandardBasicTypes.STRING)
                .addScalar("categoryId", StandardBasicTypes.LONG)
                .setResultTransformer((tuple, aliases) -> new AcademyByLocationWithNotLikeRepositoryResponse(
                    (Long) tuple[0],
                    (String) tuple[1],
                    (String) tuple[2],
                    (String) tuple[3],
                    (Double) tuple[4],
                    (Double) tuple[5],
                    (String) tuple[6],
                    (Long) tuple[7]
                ))
                .getResultList();

        return AcademyByLocationWithCursorNotLikeRepositoryResponse.of(
            academiesByLocation,
            getBeforeLastId(academiesByLocation),
            isHasNest(academiesByLocation.size())
        );
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

        List<AcademyByLocationWithLikeRepositoryResponse> academiesByLocation =
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
                .setResultTransformer((tuple, aliases) -> new AcademyByLocationWithLikeRepositoryResponse(
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

    public AcademiesByLocationWithScrollRepositoryResponse findAcademiesByLocation(
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

        List<AcademyByLocationWithLikeRepositoryResponse> academiesByLocation =
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
                .setResultTransformer((tuple, aliases) -> new AcademyByLocationWithLikeRepositoryResponse(
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

        return AcademiesByLocationWithScrollRepositoryResponse.of(
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

}
