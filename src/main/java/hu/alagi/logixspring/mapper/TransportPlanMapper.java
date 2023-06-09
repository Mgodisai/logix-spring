package hu.alagi.logixspring.mapper;

import hu.alagi.logixspring.dto.MilestoneDto;
import hu.alagi.logixspring.dto.SectionDto;
import hu.alagi.logixspring.dto.TransportPlanDto;
import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.Section;
import hu.alagi.logixspring.model.TransportPlan;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransportPlanMapper {

    @Mapping(target="sectionDtoList", source="sectionList")
    TransportPlanDto toDto (TransportPlan transportPlan);

    @InheritInverseConfiguration
    TransportPlan toTransportPlan (TransportPlanDto transportPlanDto);

    @Mapping(target="toMilestoneDto", source="toMilestone")
    @Mapping(target="fromMilestoneDto", source="fromMilestone")
    SectionDto toSectionDto (Section section);

    @InheritInverseConfiguration
    Section toSection (SectionDto sectionDto);

    @Mapping(target="addressDto", source="address")
    MilestoneDto toMilestoneDto (Milestone milestone);

    @InheritInverseConfiguration
    Milestone toMilestone (MilestoneDto milestoneDto);
}
