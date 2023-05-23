package hu.alagi.logixspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Milestone extends AbstractEntity<Long>{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;
    private LocalDateTime plannedTime;

    public Milestone(Long id, Address address, LocalDateTime plannedTime) {
        super(id);
        this.address = address;
        this.plannedTime = plannedTime;
    }

    public Milestone(Address address, LocalDateTime plannedTime) {
        this.address = address;
        this.plannedTime = plannedTime;
    }

    public Milestone() {
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Milestone milestone = (Milestone) o;
        return this.getId() != null && this.getId().equals(milestone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
