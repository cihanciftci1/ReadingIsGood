package com.getir.readingisgood.model.response.statistics;

import com.getir.readingisgood.dto.StatisticsDTO;
import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class GetCustomerMonthlyStatisticsResponse extends BaseResponse {
    private Map<String,Object> data;

    public GetCustomerMonthlyStatisticsResponse(String message, List<StatisticsDTO> statisticsDTOs){
        super(message);
        this.data = Map.of("statistics", statisticsDTOs);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
