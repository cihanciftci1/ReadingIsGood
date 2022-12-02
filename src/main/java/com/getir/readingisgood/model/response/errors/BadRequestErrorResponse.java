package com.getir.readingisgood.model.response.errors;

import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BadRequestErrorResponse extends BaseResponse {

    public BadRequestErrorResponse(String message){
        super(message);
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.setSuccess(false);
    }
}
