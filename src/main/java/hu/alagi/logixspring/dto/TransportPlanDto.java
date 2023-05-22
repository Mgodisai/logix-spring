package hu.alagi.logixspring.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class TransportPlanDto {

    @JsonView(Views.BaseView.class)
    private Long id;
    @JsonView(Views.BaseView.class)
    private Double expectedRevenue;

    @JsonView(Views.BaseView.class)
    private List<SectionDto> sectionDtoList;

    public TransportPlanDto(Long id, Double expectedRevenue, List<SectionDto> sectionDtoList) {
        this.id = id;
        this.expectedRevenue = expectedRevenue;
        this.sectionDtoList = sectionDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getExpectedRevenue() {
        return expectedRevenue;
    }

    public void setExpectedRevenue(Double expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }

    public List<SectionDto> getSectionDtoList() {
        return sectionDtoList;
    }

    public void setSectionDtoList(List<SectionDto> sectionDtoList) {
        this.sectionDtoList = sectionDtoList;
    }
}
