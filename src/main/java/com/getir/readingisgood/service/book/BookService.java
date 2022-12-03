package com.getir.readingisgood.service.book;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.model.request.book.CreateBookRequest;
import com.getir.readingisgood.model.request.book.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;

public interface BookService {
    BaseResponse create(final CreateBookRequest createBookRequest);
    BaseResponse updateStock(final UpdateBookStockRequest updateBookStockRequest);
    Book findByIdAndCheckStock(final Integer id, final int quantity);
}
