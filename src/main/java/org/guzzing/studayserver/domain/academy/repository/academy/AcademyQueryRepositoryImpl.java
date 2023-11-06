package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {

    private final EntityManager em;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<AcademiesByLocation> findAcademiesByLocation(String pointFormat) {

        Query query = em.createNativeQuery(
                "SELECT a.id AS academyId, a.academy_name AS academyName, a.contact , a.full_address AS fullAddress," +
                        " a.area_of_expertise AS areaOfExpertise, a.latitude AS latitude , a.longitude AS longitude FROM academies AS a "
                        +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", a.point)=1");

        List<AcademiesByLocation> academies = query.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("academyId", StandardBasicTypes.LONG)
                .addScalar("academyName", StandardBasicTypes.STRING)
                .addScalar("contact", StandardBasicTypes.STRING)
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

}
