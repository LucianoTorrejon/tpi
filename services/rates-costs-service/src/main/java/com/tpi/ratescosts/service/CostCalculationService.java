package com.tpi.ratescosts.service;

import com.tpi.ratescosts.dto.CostCalculationRequest;
import com.tpi.ratescosts.dto.CostCalculationResponse;

public interface CostCalculationService {
    CostCalculationResponse calculateCost(CostCalculationRequest request);
}
