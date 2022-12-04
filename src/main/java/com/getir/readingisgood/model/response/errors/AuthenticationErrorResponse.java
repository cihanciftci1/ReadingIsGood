package com.getir.readingisgood.model.response.errors;

import com.getir.readingisgood.model.response.BaseResponse;
import org.springframework.http.HttpStatus;


public class AuthenticationErrorResponse extends BaseResponse {

    public AuthenticationErrorResponse(String message){
        super(message);
        this.setStatus(HttpStatus.UNAUTHORIZED);
        this.setSuccess(false);
    }
}
