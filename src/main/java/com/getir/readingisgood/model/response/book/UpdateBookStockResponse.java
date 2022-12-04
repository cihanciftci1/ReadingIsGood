package com.getir.readingisgood.model.response.book;

import com.getir.readingisgood.dto.BookDTO;
import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class UpdateBookStockResponse extends BaseResponse {
    private Map<String, BookDTO> data;

    public UpdateBookStockResponse(String message, BookDTO book){
        super(message);
        this.data = Map.of("book", book);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
