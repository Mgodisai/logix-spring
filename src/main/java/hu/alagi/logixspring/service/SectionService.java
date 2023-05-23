package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Section;
import hu.alagi.logixspring.repository.SectionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public void saveSectionList(List<Section> sections) {
        for (var s : sections) {
            createSection(s);
        }
    }

    @Transactional
    public Optional<Section> createSection(Section section) {
        if (sectionRepository.existsWithAlreadyUsedMilestone(section.getFromMilestone(), section.getToMilestone())
        ) {
            throw new IllegalStateException("A section with the same fromMilestone or toMilestone already exists");
        } else {
            return Optional.of(sectionRepository.save(section));
        }
    }

    @Transactional
    public void deleteAll() {
        sectionRepository.deleteAll();
    }
}
