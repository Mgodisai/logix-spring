package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.repository.MilestoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    @Transactional
    public void saveMilestoneList(List<Milestone> milestones) {
        milestoneRepository.saveAll(milestones);
    }

    @Transactional
    public void deleteAll() {
        milestoneRepository.deleteAll();
    }

    public Optional<Milestone> findMilestoneById(Long milestoneId) {
        return milestoneRepository.findById(milestoneId);
    }
}
