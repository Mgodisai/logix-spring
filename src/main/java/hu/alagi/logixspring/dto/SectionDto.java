package hu.alagi.logixspring.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class SectionDto {

    @JsonView(Views.BaseView.class)
    private Long id;
    @JsonView(Views.BaseView.class)
    private MilestoneDto fromMilestoneDto;
    @JsonView(Views.BaseView.class)
    private MilestoneDto toMilestoneDto;

    @JsonView(Views.ExtendedView.class)
    private TransportPlanDto transportPlanDto;

    @JsonView(Views.BaseView.class)
    private Integer sectionOrderIndex;

    public SectionDto(Long id, MilestoneDto fromMilestoneDto, MilestoneDto toMilestoneDto, TransportPlanDto transportPlanDto, Integer sectionOrderIndex) {
        this.id = id;
        this.fromMilestoneDto = fromMilestoneDto;
        this.toMilestoneDto = toMilestoneDto;
        this.transportPlanDto = transportPlanDto;
        this.sectionOrderIndex = sectionOrderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MilestoneDto getFromMilestoneDto() {
        return fromMilestoneDto;
    }

    public void setFromMilestoneDto(MilestoneDto fromMilestoneDto) {
        this.fromMilestoneDto = fromMilestoneDto;
    }

    public MilestoneDto getToMilestoneDto() {
        return toMilestoneDto;
    }

    public void setToMilestoneDto(MilestoneDto toMilestoneDto) {
        this.toMilestoneDto = toMilestoneDto;
    }

    public Integer getSectionOrderIndex() {
        return sectionOrderIndex;
    }

    public void setSectionOrderIndex(Integer sectionOrderIndex) {
        this.sectionOrderIndex = sectionOrderIndex;
    }

    public TransportPlanDto getTransportPlanDto() {
        return transportPlanDto;
    }

    public void setTransportPlanDto(TransportPlanDto transportPlanDto) {
        this.transportPlanDto = transportPlanDto;
    }
}
