package com.getir.readingisgood.model.response.book;

import com.getir.readingisgood.dto.BookDTO;
import com.getir.readingisgood.model.response.BaseResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@Setter
public class BookCreateResponse extends BaseResponse {
    private Map<String,Object> data;

    public BookCreateResponse(String message, BookDTO book){
        super(message);
        this.data = Map.of("book", book);
        this.setStatus(HttpStatus.OK);
        this.setSuccess(true);
    }
}
