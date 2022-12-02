package com.getir.readingisgood.model.response.customer;

import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@Setter
public class CustomerLoginResponse extends BaseResponse {

    private Map<String,Object> data;

    public CustomerLoginResponse(String message, String token){
        super(message);
        this.data = Map.of("token", Constants.JWT_TOKEN_TYPE + " " + token);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
