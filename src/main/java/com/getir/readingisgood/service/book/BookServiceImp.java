package com.getir.readingisgood.service.book;

import com.getir.readingisgood.dto.BookDTO;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.book.BookCreateRequest;
import com.getir.readingisgood.model.request.book.BookUpdateStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.book.BookCreateResponse;
import com.getir.readingisgood.model.response.book.BookUpdateStockResponse;
import com.getir.readingisgood.model.response.errors.BadRequestErrorResponse;
import com.getir.readingisgood.model.response.errors.NotFoundErrorResponse;
import com.getir.readingisgood.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{
    private final BookRepository bookRepository;


    public BaseResponse create(final BookCreateRequest bookCreateRequest){
        if(bookRepository.findByTitle(bookCreateRequest.getTitle()).isPresent()){
            return new BadRequestErrorResponse(Constants.BOOK_ALREADY_EXISTS);
        }
        Book book = Book.builder()
                .title(bookCreateRequest.getTitle())
                .stock(bookCreateRequest.getStock())
                .price(bookCreateRequest.getPrice())
                .build();

        bookRepository.save(book);

        return new BookCreateResponse(Constants.BOOK_CREATED_SUCCESSFULLY,
                BookDTO.builder()
                    .title(book.getTitle())
                    .stock(book.getStock())
                    .price(book.getPrice())
                    .build());
    }


    public BaseResponse updateStock(final BookUpdateStockRequest bookUpdateStockRequest){
        if(bookUpdateStockRequest.getStock()<= 0){
            return new BadRequestErrorResponse(Constants.BOOK_STOCK_CANT_BE_NEGATIVE);
        }
        if(bookRepository.findById(bookUpdateStockRequest.getId()).isEmpty()){
            return new NotFoundErrorResponse(Constants.BOOK_NOT_FOUND);
        }

        Book updatedBook = bookRepository.findById(bookUpdateStockRequest.getId()).get();
        updatedBook.setStock(bookUpdateStockRequest.getStock());
        bookRepository.save(updatedBook);

        return new BookUpdateStockResponse(Constants.BOOK_STOCK_UPDATED_SUCCESFULLY,
                BookDTO.builder()
                        .title(updatedBook.getTitle())
                        .stock(updatedBook.getStock())
                        .price(updatedBook.getPrice())
                        .build());
    }
}
