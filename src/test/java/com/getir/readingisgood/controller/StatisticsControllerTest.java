package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.dto.StatisticsDTO;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.response.statistics.GetCustomerMonthlyStatisticsResponse;
import com.getir.readingisgood.service.statistic.StatisticService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StatisticsControllerTest {
    @MockBean
    private StatisticService statisticService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void statistics_controller_get_customer_monthly_statistics_should_return_statistics() throws Exception{
        //given
        Long customerId = 1L;

        GetCustomerMonthlyStatisticsResponse expectedResponse = new GetCustomerMonthlyStatisticsResponse(Constants.GET_STATISTICS_SUCCESSFUL,
                List.of(StatisticsDTO.builder()
                        .month("January")
                        .totalOrderCount("1")
                        .totalBookCount("1")
                        .totalPurchaseAmount("1.0").build()));

        //when
        when(statisticService.getCustomerMonthlyStatistics(customerId)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(get("/statistics/customer/{customerId}", customerId));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())));
    }
}