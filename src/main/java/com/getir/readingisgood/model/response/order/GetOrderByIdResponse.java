package com.getir.readingisgood.model.response.order;

import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class GetOrderByIdResponse extends BaseResponse {
    private Map<String, OrderDTO> data;

    public GetOrderByIdResponse(String message, OrderDTO orderDTO){
        super(message);
        this.data = Map.of("order", orderDTO);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
