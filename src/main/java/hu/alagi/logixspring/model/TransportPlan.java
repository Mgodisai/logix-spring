package hu.alagi.logixspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class TransportPlan extends AbstractEntity<Long>{
    private Double expectedRevenue;

    @OneToMany(mappedBy=Section_.TRANSPORT_PLAN)
    private List<Section> sectionList;

    public TransportPlan(Long id, Double expectedRevenue, List<Section> sectionList) {
        super(id);
        this.expectedRevenue = expectedRevenue;
        this.sectionList = sectionList;
    }

    public TransportPlan(Double expectedRevenue, List<Section> sectionList) {
        this.expectedRevenue = expectedRevenue;
        this.sectionList = sectionList;
    }

    public TransportPlan() {
    }

    public Double getExpectedRevenue() {
        return expectedRevenue;
    }

    public void setExpectedRevenue(Double expectedRevenue) {
        this.expectedRevenue = expectedRevenue;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }
}
