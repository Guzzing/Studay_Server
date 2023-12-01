package org.guzzing.studayserver.domain.child.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ChildScheduleReadService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ChildWithScheduleResult> findScheduleByMemberIdAndDate(Long memberId, LocalDate scheduleDate) {
        String sql = "SELECT c.child_id as child_id, " +
                "acs.schedule_date as schedule_date, " +
                "acs.lesson_start_time as lesson_start_time, " +
                "acs.lesson_end_time as lesson_end_time, " +
                "a.academy_name as academy_name, " +
                "l.subject as lesson_subject " +
                "FROM children c " +
                "INNER JOIN dashboards d ON c.child_id = d.child_id " +
                "INNER JOIN academies a ON d.academy_id = a.id " +
                "INNER JOIN lessons l ON l.id = d.lesson_id " +
                "INNER JOIN academy_time_templates att ON d.id = att.dashboard_id " +
                "INNER JOIN academy_schedules acs ON acs.academy_time_template_id = att.id " +
                "WHERE c.member_id = :memberId " +
                "AND acs.schedule_date = :scheduleDate";

        Query query = entityManager.createNativeQuery(sql, "ChildWithScheduleResultSetMapping");
        query.setParameter("memberId", memberId);
        query.setParameter("scheduleDate", scheduleDate);

        return query.getResultList();
    }
}
