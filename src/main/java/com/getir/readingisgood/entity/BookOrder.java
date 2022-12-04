package com.getir.readingisgood.entity;

import com.getir.readingisgood.enums.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book_order")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max=80)
    private String title;
    @NotNull
    private double price;
    @NotNull
    @PositiveOrZero(message = Constants.ORDER_QUANTITY_CANT_BE_NEGATIVE)
    private int quantity;

    @NotNull
    private Integer bookId;
    @NotNull
    private Long customerId;
    @NotNull
    private Long orderId;

}
