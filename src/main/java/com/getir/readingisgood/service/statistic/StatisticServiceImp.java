package com.getir.readingisgood.service.statistic;

import com.getir.readingisgood.dto.StatisticsDTO;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.statistics.GetCustomerMonthlyStatisticsResponse;
import com.getir.readingisgood.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImp implements StatisticService{
    private final OrderRepository orderRepository;

    @Override
    public BaseResponse getCustomerMonthlyStatistics(final Long id) {
        List<List<String>> statistics = orderRepository.getCustomerMonthlyStatistics(id);
        List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
        for(List<String> statistic : statistics){
            statisticsDTOs.add(StatisticsDTO.builder()
                    .month(statistic.get(0).toString())
                    .totalOrderCount(statistic.get(1).toString())
                    .totalBookCount(statistic.get(2).toString())
                    .totalPurchaseAmount(statistic.get(3).toString())
                    .build());
        }
        return new GetCustomerMonthlyStatisticsResponse(Constants.GET_STATISTICS_SUCCESSFUL, statisticsDTOs);
    }
}
