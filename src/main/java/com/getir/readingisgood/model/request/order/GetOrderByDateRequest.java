package com.getir.readingisgood.model.request.order;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetOrderByDateRequest {
    private Date startDate;
    private Date endDate;
}
