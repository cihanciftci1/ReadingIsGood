package com.getir.readingisgood.model.request.order;

import lombok.Data;

import java.util.Date;

@Data
public class GetOrderByDateRequest {
    private Date startDate;
    private Date endDate;
}
