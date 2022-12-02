package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.book.BookCreateRequest;
import com.getir.readingisgood.model.request.book.BookUpdateStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.service.book.BookServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImp bookService;

    @PostMapping("/create")
    public BaseResponse create(@RequestBody final BookCreateRequest bookCreateRequest){
        return bookService.create(bookCreateRequest);
    }

    @PostMapping("/updateStock")
    public BaseResponse updateStock(@RequestBody final BookUpdateStockRequest bookUpdateStockRequest){
        return bookService.updateStock(bookUpdateStockRequest);
    }
}
