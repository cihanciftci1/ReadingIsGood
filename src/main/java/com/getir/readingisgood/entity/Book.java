package com.getir.readingisgood.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max=80)
    @Column(unique = true)
    private String title;
    @NotNull
    private double price;
    @NotNull
    @PositiveOrZero(message = Constants.BOOK_STOCK_CANT_BE_NEGATIVE)
    private int stock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;


    @PrePersist
    private void whenCreate(){
        createdDate = new Date();
        updatedDate = new Date();
    }


    @PreUpdate
    private void whenUpdate(){
        updatedDate = new Date();
    }
}
