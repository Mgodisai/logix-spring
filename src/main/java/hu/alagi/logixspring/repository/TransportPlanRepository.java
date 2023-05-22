package hu.alagi.logixspring.repository;

import hu.alagi.logixspring.model.TransportPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

    @EntityGraph("graph.TransportPlanWithSectionsAndMilestonesAndAddresses")
    Optional<TransportPlan> findTransportPlanById(Long id);
}
