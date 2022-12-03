package com.getir.readingisgood.service.statistic;

import com.getir.readingisgood.dto.StatisticsDTO;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.statistics.GetCustomerMonthlyStatisticsResponse;
import com.getir.readingisgood.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImp implements StatisticService{
    private final OrderRepository orderRepository;

    @Override
    public BaseResponse getCustomerMonthlyStatistics(final Long id) {
        List<Tuple> statistics = orderRepository.getCustomerMonthlyStatistics(id);
        List<StatisticsDTO> statisticsDTOs = new ArrayList<>();
        for(Tuple tuple : statistics){
            statisticsDTOs.add(StatisticsDTO.builder()
                    .month(tuple.get(0).toString())
                    .totalOrderCount(tuple.get(1).toString())
                    .totalBookCount(tuple.get(2).toString())
                    .totalPurchaseAmount(tuple.get(3).toString())
                    .build());
        }
        return new GetCustomerMonthlyStatisticsResponse(Constants.GET_STATISTICS_SUCCESSFUL, statisticsDTOs);
    }
}
