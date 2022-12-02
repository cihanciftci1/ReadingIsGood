package com.getir.readingisgood.model.response.customer;

import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class CustomerRegisterResponse extends BaseResponse {
    public CustomerRegisterResponse(String message){
        super(message);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
