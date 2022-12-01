package com.getir.readingisgood.model.response.customer;

import com.getir.readingisgood.enums.IConstants;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerLoginResponse {

    private String token;

    public CustomerLoginResponse(String token){
        this.token = IConstants.JWT_TOKEN_TYPE + " " + token;
    }
}
