package com.getir.readingisgood.service.statistic;

import com.getir.readingisgood.model.response.BaseResponse;

public interface StatisticService {
    BaseResponse getCustomerMonthlyStatistics(final Long id);
}
