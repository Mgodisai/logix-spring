package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.repository.MilestoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public void saveMilestoneList(List<Milestone> milestones) {
        milestoneRepository.saveAll(milestones);
    }

    @Transactional
    public void deleteAll() {
        milestoneRepository.deleteAll();
    }
}
