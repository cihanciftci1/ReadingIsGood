package com.getir.readingisgood.model.request.customer;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class RegisterCustomerRequest {
    @NotBlank
    @Size(max=20)
    private String username;
    @NotBlank
    @Size(max = 50)
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
}
