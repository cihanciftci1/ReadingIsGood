package com.getir.readingisgood.model.request.customer;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CustomerLoginRequest {
    @NotBlank
    @Size(max=20)
    private String username;
    @NotBlank
    @Size(max = 120)
    private String password;
}
