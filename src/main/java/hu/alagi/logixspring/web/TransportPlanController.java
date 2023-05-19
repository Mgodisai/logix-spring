package hu.alagi.logixspring.web;

import hu.alagi.logixspring.dto.MilestoneDelayDto;
import hu.alagi.logixspring.model.TransportPlan;
import hu.alagi.logixspring.service.TransportPlanService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    private final TransportPlanService transportPlanService;

    public TransportPlanController(TransportPlanService transportPlanService) {
        this.transportPlanService = transportPlanService;
    }

    @PostMapping("/{id}/delay")
    public TransportPlan registerDelay(@RequestBody @Valid MilestoneDelayDto milestoneDelayDto, @PathVariable Long id) {
        return transportPlanService.handleDelay(id, milestoneDelayDto);
    }

}
