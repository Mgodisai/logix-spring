package hu.alagi.logixspring.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedEntityGraph(
        name = "graph.TransportPlanWithSectionsAndMilestonesAndAddresses",
        attributeNodes = @NamedAttributeNode(value = "sectionList", subgraph = "subgraph.section"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph.section",
                        attributeNodes = {
                        @NamedAttributeNode(value = "toMilestone", subgraph = "subgraph.milestone"),
                        @NamedAttributeNode(value = "fromMilestone", subgraph = "subgraph.milestone")
                }),
                @NamedSubgraph(name = "subgraph.milestone",
                        attributeNodes = {
                                @NamedAttributeNode(value = "address")
                        })
        })
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
