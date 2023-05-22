package hu.alagi.logixspring.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

public class MilestoneDto {

    @JsonView(Views.BaseView.class)
    private Long id;
    @JsonView(Views.BaseView.class)
    private AddressDto addressDto;
    @JsonView(Views.BaseView.class)
    private LocalDateTime plannedTime;

    public MilestoneDto(Long id, AddressDto addressDto, LocalDateTime plannedTime) {
        this.id = id;
        this.addressDto = addressDto;
        this.plannedTime = plannedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }
}
