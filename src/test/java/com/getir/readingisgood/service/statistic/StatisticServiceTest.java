package com.getir.readingisgood.service.statistic;

import com.getir.readingisgood.dto.StatisticsDTO;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.statistics.GetCustomerMonthlyStatisticsResponse;
import com.getir.readingisgood.repository.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private StatisticServiceImp statisticService;


    @Test
    void statistics_service_should_return_monthly_statistics_for_customer(){
        //given
        Long customerId = 1L;

        List<List<String>> statistics = List.of(List.of("January", "1", "1", "1.0"));

        GetCustomerMonthlyStatisticsResponse expectedResponse = new GetCustomerMonthlyStatisticsResponse(Constants.GET_STATISTICS_SUCCESSFUL,
                List.of(StatisticsDTO.builder()
                        .month("January")
                        .totalOrderCount("1")
                        .totalBookCount("1")
                        .totalPurchaseAmount("1.0").build()));

        //when
        when(orderRepository.getCustomerMonthlyStatistics(any(Long.class))).thenReturn(statistics);
        BaseResponse actualResponse = statisticService.getCustomerMonthlyStatistics(customerId);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());

    }

}