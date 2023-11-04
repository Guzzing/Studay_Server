package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.model.vo.Location;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;

import java.util.List;

public class AcademyQueryRepositoryImpl implements AcademyQueryRepository {

    private static final String LINESTRING_SQL = "'LINESTRING(%f %f, %f %f)')";

    private final EntityManager em;

    public AcademyQueryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<AcademiesByLocation> findAcademiesByLocation(Location northEast, Location southWest) {

        String pointFormat = String.format(
                LINESTRING_SQL,
                 northEast.getLongitude(),northEast.getLatitude(), southWest.getLongitude(),southWest.getLatitude()
        );

        Query query = em.createNativeQuery(
                "SELECT a.id, a.name, a.contact, a.full_address, a.area_of_expertise FROM academies AS a " +
                        "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", a.point)=1"
        );

        List<AcademiesByLocation> academies = query.unwrap(org.hibernate.query.NativeQuery.class)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("contact", StandardBasicTypes.STRING)
                .addScalar("full_address", StandardBasicTypes.STRING)
                .addScalar("area_of_expertise", StandardBasicTypes.STRING)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(Object[] tuple, String[] aliases) {
                                return new AcademiesByLocation(
                                        (Long) tuple[0],
                                        (String) tuple[1],
                                        (String) tuple[2],
                                        (String) tuple[3],
                                        (String) tuple[4]
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
