package com.getir.readingisgood.service.book;

import com.getir.readingisgood.dto.BookDTO;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.book.CreateBookRequest;
import com.getir.readingisgood.model.request.book.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.book.CreateBookResponse;
import com.getir.readingisgood.model.response.book.UpdateBookStockResponse;
import com.getir.readingisgood.model.response.errors.BadRequestErrorResponse;
import com.getir.readingisgood.model.response.errors.NotFoundErrorResponse;
import com.getir.readingisgood.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{
    private final BookRepository bookRepository;

    @Override
    public BaseResponse create(final CreateBookRequest createBookRequest){
        if(Objects.nonNull(bookRepository.findByTitle(createBookRequest.getTitle()))){
            return new BadRequestErrorResponse(Constants.BOOK_ALREADY_EXISTS);
        }
        if(createBookRequest.getStock()<= 0){
            return new BadRequestErrorResponse(Constants.BOOK_STOCK_CANT_BE_NEGATIVE);
        }
        Book book = Book.builder()
                .title(createBookRequest.getTitle())
                .stock(createBookRequest.getStock())
                .price(createBookRequest.getPrice())
                .build();

        bookRepository.save(book);

        return new CreateBookResponse(Constants.BOOK_CREATED_SUCCESSFULLY,
                BookDTO.builder()
                    .title(book.getTitle())
                    .stock(book.getStock())
                    .price(book.getPrice())
                    .build());
    }

    @Override
    public BaseResponse updateStock(final UpdateBookStockRequest updateBookStockRequest){
        if(updateBookStockRequest.getStock()<= 0){
            return new BadRequestErrorResponse(Constants.BOOK_STOCK_CANT_BE_NEGATIVE);
        }
        if(bookRepository.findById(updateBookStockRequest.getId()).isEmpty()){
            return new NotFoundErrorResponse(Constants.BOOK_NOT_FOUND);
        }

        Book updatedBook = bookRepository.findById(updateBookStockRequest.getId()).get();
        updatedBook.setStock(updateBookStockRequest.getStock());
        bookRepository.save(updatedBook);

        return new UpdateBookStockResponse(Constants.BOOK_STOCK_UPDATED_SUCCESSFULLY,
                BookDTO.builder()
                        .title(updatedBook.getTitle())
                        .stock(updatedBook.getStock())
                        .price(updatedBook.getPrice())
                        .build());
    }

    @Override
    public Book findByIdAndCheckStock(final Integer id, final int quantity) {
        return bookRepository.findByIdAndStockGreaterThanEqual(id, quantity);
    }


}
