package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticService statisticService;

    @GetMapping("/customer/{customerId}")
    BaseResponse getCustomerMonthlyStatistics(@PathVariable final Long customerId){
        return statisticService.getCustomerMonthlyStatistics(customerId);
    }

}
