package hu.alagi.logixspring.service;

import hu.alagi.logixspring.config.LogixConfigProperties;
import hu.alagi.logixspring.dto.MilestoneDelayDto;
import hu.alagi.logixspring.exception.EntityIdMismatchException;
import hu.alagi.logixspring.exception.EntityNotExistsWithGivenIdException;
import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.Section;
import hu.alagi.logixspring.model.TransportPlan;
import hu.alagi.logixspring.repository.MilestoneRepository;
import hu.alagi.logixspring.repository.TransportPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransportPlanService {

    private final TransportPlanRepository transportPlanRepository;
    private final MilestoneRepository milestoneRepository;
    private final LogixConfigProperties logixConfigProperties;

    public TransportPlanService(TransportPlanRepository transportPlanRepository, MilestoneRepository milestoneRepository, LogixConfigProperties logixConfigProperties) {
        this.transportPlanRepository = transportPlanRepository;
        this.milestoneRepository = milestoneRepository;
        this.logixConfigProperties = logixConfigProperties;
    }

    @Transactional
    public TransportPlan handleDelay(Long transportPlanId, MilestoneDelayDto milestoneDelayDto) {
        Optional<TransportPlan> requestedTransportPlan = transportPlanRepository.findTransportPlanById(transportPlanId);
        if (requestedTransportPlan.isEmpty()) {
            throw new EntityNotExistsWithGivenIdException(transportPlanId, TransportPlan.class);
        }
        Long milestoneId = milestoneDelayDto.getMilestoneId();
        Optional<Milestone> affectedMilestone = milestoneRepository.findById(milestoneId);
        if (affectedMilestone.isEmpty()) {
            throw new EntityNotExistsWithGivenIdException(milestoneId, Milestone.class);
        }
        TransportPlan updatingTransportPlan = requestedTransportPlan.get();
        updateMilestonesAndRevenue(updatingTransportPlan,affectedMilestone.get(), milestoneDelayDto.getDelayInMinutes());
        return saveTransportPlan(updatingTransportPlan);
    }

    @Transactional
    public TransportPlan saveTransportPlan(TransportPlan transportPlan) {
        return transportPlanRepository.save(transportPlan);
    }

    public Optional<TransportPlan> findTransportPlanById(Long id) {
        return transportPlanRepository.findTransportPlanById(id);
    }
    private void updateMilestonesAndRevenue(TransportPlan transportPlan, Milestone milestone, Integer delayInMinutes) {
        List<Section> sections = transportPlan.getSectionList();
        sections.sort(Comparator.comparing(Section::getSectionOrderIndex));
        boolean isNextSectionAffected = false;
        boolean affectedMilestoneBelongsToThisTransportPlan = false;
        for (Section section : sections) {
            if (isNextSectionAffected) {
                updateMilestoneTime(section.getFromMilestone(), delayInMinutes);
                break;
            }
            if (section.getFromMilestone().equals(milestone)) {
                updateMilestoneTime(section.getFromMilestone(), delayInMinutes);
                updateMilestoneTime(section.getToMilestone(), delayInMinutes);
                affectedMilestoneBelongsToThisTransportPlan = true;
                break;
            } else if (section.getToMilestone().equals(milestone)) {
                updateMilestoneTime(section.getToMilestone(), delayInMinutes);
                affectedMilestoneBelongsToThisTransportPlan = true;
                isNextSectionAffected = true;
            }
        }
        if (!affectedMilestoneBelongsToThisTransportPlan) {
            throw new EntityIdMismatchException("The affected milestone does not belong to this transport plan with id: "+ transportPlan.getId());
        }
        updateExpectedRevenue(transportPlan, delayInMinutes);
    }

    private void updateMilestoneTime(Milestone milestone, Integer delayInMinutes) {
        LocalDateTime plannedTime = milestone.getPlannedTime();
        milestone.setPlannedTime(plannedTime.plusMinutes(delayInMinutes));
    }

    private void updateExpectedRevenue(TransportPlan transportPlan, Integer delayInMinutes) {
        double reductionRate = getReductionRate(delayInMinutes);
        double expectedRevenue = transportPlan.getExpectedRevenue();
        double reducedRevenue = expectedRevenue - (expectedRevenue * reductionRate / 100);
        transportPlan.setExpectedRevenue(reducedRevenue);
    }

    public Double getReductionRate(Integer delayInMinutes) {
        return logixConfigProperties.getValueLimits()
                .entrySet()
                .stream()
                .filter(x->x.getKey()<=delayInMinutes)
                .max(Comparator.comparingDouble(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .orElse(0d);
    }

    public void deleteAll() {
        transportPlanRepository.deleteAll();
    }
}
