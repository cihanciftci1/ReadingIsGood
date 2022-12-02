package com.getir.readingisgood.service.book;

import com.getir.readingisgood.model.request.book.BookCreateRequest;
import com.getir.readingisgood.model.request.book.BookUpdateStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;

public interface BookService {
    BaseResponse create(final BookCreateRequest bookCreateRequest);
    BaseResponse updateStock(final BookUpdateStockRequest bookUpdateStockRequest);
}
