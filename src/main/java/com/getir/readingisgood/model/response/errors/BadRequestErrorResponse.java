package com.getir.readingisgood.model.response.errors;

import com.getir.readingisgood.model.response.BaseResponse;
import org.springframework.http.HttpStatus;


public class BadRequestErrorResponse extends BaseResponse {

    public BadRequestErrorResponse(String message){
        super(message);
        this.setStatus(HttpStatus.BAD_REQUEST);
        this.setSuccess(false);
    }
}
