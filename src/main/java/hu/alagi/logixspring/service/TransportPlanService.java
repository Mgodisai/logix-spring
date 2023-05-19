package hu.alagi.logixspring.service;

import hu.alagi.logixspring.dto.MilestoneDelayDto;
import hu.alagi.logixspring.exception.EntityNotExistsWithGivenIdException;
import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.TransportPlan;
import hu.alagi.logixspring.repository.MilestoneRepository;
import hu.alagi.logixspring.repository.TransportPlanRepository;
import org.springframework.stereotype.Service;

@Service
public class TransportPlanService {

    private final TransportPlanRepository transportPlanRepository;
    private final MilestoneRepository milestoneRepository;

    public TransportPlanService(TransportPlanRepository transportPlanRepository, MilestoneRepository milestoneRepository) {
        this.transportPlanRepository = transportPlanRepository;
        this.milestoneRepository = milestoneRepository;
    }

    public void saveTransportPlan(TransportPlan transportPlan) {
        transportPlanRepository.save(transportPlan);
    }

    public TransportPlan handleDelay(Long transportPlanId, MilestoneDelayDto milestoneDelayDto) {
        if (!transportPlanRepository.existsById(transportPlanId)) {
            throw new EntityNotExistsWithGivenIdException(transportPlanId, TransportPlan.class);
        }
        Long milestoneId = milestoneDelayDto.getMilestoneId();
        if (!milestoneRepository.existsById(milestoneId)) {
            throw new EntityNotExistsWithGivenIdException(milestoneId, Milestone.class);
        }

        return null;
    }

}
