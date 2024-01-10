package org.guzzing.studayserver.domain.child.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.guzzing.studayserver.domain.child.model.Child;
import org.springframework.stereotype.Repository;

@Repository
public class ChildRepositoryImpl implements ChildRepository{

    private final ChildJpaRepository childJpaRepository;

    public ChildRepositoryImpl(ChildJpaRepository childJpaRepository) {
        this.childJpaRepository = childJpaRepository;
    }

    @Override
    public Optional<Child> findByIdAndMemberId(Long childId, Long memberId) {
        return childJpaRepository.findByIdAndMemberId(childId, memberId);
    }

    @Override
    public void deleteByMemberId(long memberId) {
        childJpaRepository.deleteByMemberId(memberId);
    }

    @Override
    public Child save(Child child) {
        return childJpaRepository.save(child);
    }

    @Override
    public Optional<Child> findById(Long childId) {
        return childJpaRepository.findById(childId);
    }

    @Override
    public List<Child> findAll() {
        return childJpaRepository.findAll();
    }
}
