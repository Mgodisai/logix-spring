package hu.alagi.logixspring.repository;

import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findSectionByFromMilestoneOrToMilestone(Milestone fromMilestone, Milestone toMilestone);
}
