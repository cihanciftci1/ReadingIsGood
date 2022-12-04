package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.book.CreateBookRequest;
import com.getir.readingisgood.model.request.book.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public BaseResponse createBook(@RequestBody final CreateBookRequest createBookRequest){
        return bookService.create(createBookRequest);
    }

    @PostMapping("/updateStock")
    public BaseResponse updateBookStock(@RequestBody final UpdateBookStockRequest updateBookStockRequest){
        return bookService.updateStock(updateBookStockRequest);
    }
}
