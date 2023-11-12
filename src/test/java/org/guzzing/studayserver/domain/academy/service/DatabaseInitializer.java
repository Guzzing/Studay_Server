package org.guzzing.studayserver.domain.academy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initializeDatabase();
    }

    private void initializeDatabase() {
        // 인덱스 존재 여부 확인
        String checkIndexQuery = "SELECT COUNT(*) FROM information_schema.statistics " +
                "WHERE table_schema = DATABASE() AND table_name = 'academies' AND index_name = 'ft_index'";

        Integer count = jdbcTemplate.queryForObject(checkIndexQuery, Integer.class);

        // 인덱스가 존재하지 않으면 생성
        if (count != null && count == 0) {
            String createIndexQuery = "ALTER TABLE academies ADD FULLTEXT INDEX ft_index (academy_name) WITH PARSER ngram";
            jdbcTemplate.execute(createIndexQuery);
        }
    }
}
