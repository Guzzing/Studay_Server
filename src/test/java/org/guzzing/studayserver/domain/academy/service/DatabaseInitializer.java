package org.guzzing.studayserver.domain.academy.service;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initializeDatabase();
    }

    private void initializeDatabase() {

        String updateSIDQuery =  "UPDATE academies SET point = ST_SRID(point, 4326) WHERE ST_SRID(point) <> 4326";
        jdbcTemplate.execute(updateSIDQuery);

        String checkFulltextIndexQuery = "SELECT COUNT(*) FROM information_schema.statistics " +
                "WHERE table_schema = DATABASE() AND table_name = 'academies' AND index_name = 'ft_index'";

        String checkRIndexQuery = "SELECT COUNT(*) FROM information_schema.statistics " +
                "WHERE table_schema = DATABASE() AND table_name = 'academies' AND index_name = 'sp_index'";


        Integer fulltextIndexCount = jdbcTemplate.queryForObject(checkFulltextIndexQuery, Integer.class);
        Integer rIndexCount = jdbcTemplate.queryForObject(checkRIndexQuery,Integer.class);

        if (fulltextIndexCount != null && fulltextIndexCount == 0) {
            String createIndexQuery = "CREATE FULLTEXT INDEX ft_index ON academies (academy_name) WITH PARSER ngram";
            jdbcTemplate.execute(createIndexQuery);
        }

        if (rIndexCount != null && rIndexCount == 0) {
            String createIndexQuery = "CREATE SPATIAL INDEX sp_index ON academies (point)";
            jdbcTemplate.execute(createIndexQuery);
        }

    }
}
