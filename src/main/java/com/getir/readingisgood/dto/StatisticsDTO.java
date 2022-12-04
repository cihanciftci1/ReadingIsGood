package com.getir.readingisgood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StatisticsDTO {
    private String month;
    private String totalOrderCount;
    private String totalBookCount;
    private String totalPurchaseAmount;
}
