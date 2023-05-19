package hu.alagi.logixspring.repository;

import hu.alagi.logixspring.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
}
