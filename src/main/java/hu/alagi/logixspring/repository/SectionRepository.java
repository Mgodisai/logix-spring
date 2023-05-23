package hu.alagi.logixspring.repository;

import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(
            "SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END "+
                    "FROM Section s "+
                    "WHERE s.fromMilestone IN (:fromMilestone, :toMilestone) "+
                    "OR s.toMilestone IN (:fromMilestone, :toMilestone)")
    boolean existsWithAlreadyUsedMilestone(Milestone fromMilestone, Milestone toMilestone);
}
