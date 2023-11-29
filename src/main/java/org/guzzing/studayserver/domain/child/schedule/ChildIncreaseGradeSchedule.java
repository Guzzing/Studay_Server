package org.guzzing.studayserver.domain.child.schedule;

import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ChildIncreaseGradeSchedule {

    private final ChildRepository childRepository;

    public ChildIncreaseGradeSchedule(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 1 *")
    public void increaseGrade() {
        childRepository.findAll()
                .forEach(Child::increaseGrade);
    }
}
