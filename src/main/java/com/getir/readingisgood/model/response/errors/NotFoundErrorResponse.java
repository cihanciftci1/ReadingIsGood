package com.getir.readingisgood.model.response.errors;

import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundErrorResponse extends BaseResponse {

    public NotFoundErrorResponse(String message){
        super(message);
        this.setStatus(HttpStatus.NOT_FOUND);
        this.setSuccess(false);
    }
}
