package hu.alagi.logixspring.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.alagi.logixspring.dto.MilestoneDelayDto;
import hu.alagi.logixspring.dto.TransportPlanDto;
import hu.alagi.logixspring.dto.Views;
import hu.alagi.logixspring.mapper.TransportPlanMapper;
import hu.alagi.logixspring.model.TransportPlan;
import hu.alagi.logixspring.service.TransportPlanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    private final TransportPlanService transportPlanService;
    private final TransportPlanMapper transportPlanMapper;

    public TransportPlanController(TransportPlanService transportPlanService, TransportPlanMapper transportPlanMapper) {
        this.transportPlanService = transportPlanService;
        this.transportPlanMapper = transportPlanMapper;
    }

    @PostMapping("/{id}/delay")
    @JsonView(Views.BaseView.class)
    public TransportPlanDto registerDelay(@RequestBody @Valid MilestoneDelayDto milestoneDelayDto, @PathVariable Long id) {
        TransportPlan updatedTransportPlan = transportPlanService.handleDelay(id, milestoneDelayDto);
        return transportPlanMapper.toDto(updatedTransportPlan);
    }

}
