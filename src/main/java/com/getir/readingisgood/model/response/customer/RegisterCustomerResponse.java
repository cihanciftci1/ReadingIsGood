package com.getir.readingisgood.model.response.customer;

import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RegisterCustomerResponse extends BaseResponse {
    public RegisterCustomerResponse(String message){
        super(message);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
