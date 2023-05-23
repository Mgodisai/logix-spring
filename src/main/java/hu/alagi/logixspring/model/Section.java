package hu.alagi.logixspring.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {Section.COLUMN_FROM_MILESTONE, Section.COLUMN_TO_MILESTONE})
})
public class Section extends AbstractEntity<Long>{

    public static final String COLUMN_FROM_MILESTONE = "from_milestone_id";
    public static final String COLUMN_TO_MILESTONE = "to_milestone_id";
    public static final String COLUMN_TRANSPORT_PLAN = "transport_plan_id";


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_FROM_MILESTONE)
    private Milestone fromMilestone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_TO_MILESTONE)
    private Milestone toMilestone;
    @ManyToOne
    @JoinColumn(name = COLUMN_TRANSPORT_PLAN)
    private TransportPlan transportPlan;

    private Integer sectionOrderIndex;

    public Section(Long id, Milestone fromMilestone, Milestone toMilestone, TransportPlan transportPlan, Integer sectionOrderIndex) {
        super(id);
        this.fromMilestone = fromMilestone;
        this.toMilestone = toMilestone;
        this.transportPlan = transportPlan;
        this.sectionOrderIndex = sectionOrderIndex;
    }

    public Section(Milestone fromMilestone, Milestone toMilestone, TransportPlan transportPlan, Integer sectionOrderIndex) {
        this.fromMilestone = fromMilestone;
        this.toMilestone = toMilestone;
        this.transportPlan = transportPlan;
        this.sectionOrderIndex = sectionOrderIndex;
    }

    public Section() {
    }

    @PrePersist
    @PreUpdate
    public void checkMilestoneIds() {
        if (fromMilestone != null && toMilestone != null
                && fromMilestone.getId().equals(toMilestone.getId())) {
            throw new IllegalArgumentException("Fields must contain unique IDs");
        }
    }

    public Milestone getFromMilestone() {
        return fromMilestone;
    }

    public void setFromMilestone(Milestone fromMilestone) {
        this.fromMilestone = fromMilestone;
    }

    public Milestone getToMilestone() {
        return toMilestone;
    }

    public void setToMilestone(Milestone toMilestone) {
        this.toMilestone = toMilestone;
    }

    public TransportPlan getTransportPlan() {
        return transportPlan;
    }

    public void setTransportPlan(TransportPlan transportplan) {
        this.transportPlan = transportplan;
    }

    public Integer getSectionOrderIndex() {
        return sectionOrderIndex;
    }

    public void setSectionOrderIndex(Integer sectionOrderIndex) {
        this.sectionOrderIndex = sectionOrderIndex;
    }
}
