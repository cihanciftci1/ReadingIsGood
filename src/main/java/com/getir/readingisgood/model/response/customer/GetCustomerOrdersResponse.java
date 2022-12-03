package com.getir.readingisgood.model.response.customer;

import com.getir.readingisgood.dto.OrderDTO;
import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GetCustomerOrdersResponse extends BaseResponse {
    private Map<String, List<OrderDTO>> data;

    public GetCustomerOrdersResponse(String message, List<OrderDTO> orderDTO){
        super(message);
        this.data = Map.of("orders", orderDTO);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
