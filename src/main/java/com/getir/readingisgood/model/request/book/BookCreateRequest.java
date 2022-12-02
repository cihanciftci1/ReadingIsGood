package com.getir.readingisgood.model.request.book;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BookCreateRequest {
    @NotBlank
    @Size(max=80)
    private String title;
    @NotNull
    private double price;
    @NotNull
    private int stock;
}
