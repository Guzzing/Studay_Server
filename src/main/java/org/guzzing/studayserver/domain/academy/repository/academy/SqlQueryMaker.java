package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationRepositoryResponse;

import java.util.List;

public class SqlQueryMaker {

    private SqlQueryMaker() {
        throw new AssertionError("인스턴스를 생성할 수 없습니다.");
    }

    private static final String BLANK_QUERY = "";
    private static final int PAGE_SIZE = 10;

    public static String builderWhere() {
        return " WHERE ";
    }

    public static boolean isHasNest(int resultSize) {
        return resultSize == PAGE_SIZE;
    }

    public static String whereFilters(String formattedQuery, AcademyFilterCondition academyFilterCondition) {
        formattedQuery += whereInCategories(academyFilterCondition);
        formattedQuery += whereBetweenEducationFee(academyFilterCondition);
        return formattedQuery;
    }

    public static String whereWithinDistance(String pointFormat) {
        return String.format(" MBRContains(ST_LINESTRINGFROMTEXT(%s), a.point) ", pointFormat);
    }

    public static String whereInCategories(AcademyFilterCondition academyFilterCondition) {
        if (academyFilterCondition.categories() != null && !academyFilterCondition.categories().isEmpty()) {
            return " AND ac.category_id IN " + academyFilterCondition.categories();
        }
        return BLANK_QUERY;
    }

    public static String whereBetweenEducationFee(AcademyFilterCondition academyFilterCondition) {
        if (academyFilterCondition.desiredMinAmount() != null && academyFilterCondition.desiredMaxAmount() != null) {
            return " AND max_education_fee BETWEEN " + academyFilterCondition.desiredMinAmount() + " AND "
                + academyFilterCondition.desiredMaxAmount();
        }
        return BLANK_QUERY;
    }

    public static String makeScroll(int pageNumber, int pageSize, String formattedQuery) {
        formattedQuery += " LIMIT " + pageSize + " OFFSET " + pageNumber * pageSize;
        return formattedQuery;
    }

    public static String makeCursor(Long lastAcademyId) {
        return " AND a.id >" + lastAcademyId;
    }

    public static String limit() {
        return " LIMIT " + PAGE_SIZE;
    }

    public static String orderByDesc(String columnName) {
        return String.format(" ORDER BY %s %s ", columnName, " DESC ");
    }

    public static String orderByAsc(String columnName) {
        return String.format(" ORDER BY %s %s ", columnName, " ASC ");
    }

    public static Long getBeforeLastId(List<? extends AcademyByLocationRepositoryResponse> academiesByLocation) {
        if (academiesByLocation != null && !academiesByLocation.isEmpty()) {
            return academiesByLocation.get(academiesByLocation.size() - 1).getAcademyId();
        }
        return 0L;
    }

}
