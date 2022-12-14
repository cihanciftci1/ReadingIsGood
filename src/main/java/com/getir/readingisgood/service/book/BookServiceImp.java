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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImp implements BookService{
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImp.class);

    @Override
    public BaseResponse create(final CreateBookRequest createBookRequest){
        if(bookRepository.existsByTitle(createBookRequest.getTitle())){
            return new BadRequestErrorResponse(Constants.BOOK_ALREADY_EXISTS);
        }
        if(createBookRequest.getStock()< 0){
            return new BadRequestErrorResponse(Constants.BOOK_STOCK_CANT_BE_NEGATIVE);
        }
        Book book = Book.builder()
                .title(createBookRequest.getTitle())
                .stock(createBookRequest.getStock())
                .price(createBookRequest.getPrice())
                .build();

        bookRepository.save(book);
        logger.info("{} inserted by admin at {}", book, LocalDateTime.now());

        return new CreateBookResponse(Constants.BOOK_CREATED_SUCCESSFULLY,
                BookDTO.builder()
                    .title(book.getTitle())
                    .stock(book.getStock())
                    .price(book.getPrice())
                    .build());
    }

    @Override
    public BaseResponse updateStock(final UpdateBookStockRequest updateBookStockRequest){
        if(updateBookStockRequest.getStock() < 0){
            return new BadRequestErrorResponse(Constants.BOOK_STOCK_CANT_BE_NEGATIVE);
        }
        if(!bookRepository.existsById(updateBookStockRequest.getBookId())){
            return new NotFoundErrorResponse(Constants.BOOK_NOT_FOUND);
        }

        Book book = bookRepository.findById(updateBookStockRequest.getBookId()).get();
        book.setStock(updateBookStockRequest.getStock());
        bookRepository.save(book);
        logger.info("Book stock updated by admin for {} at {}", book, LocalDateTime.now());

        return new UpdateBookStockResponse(Constants.BOOK_STOCK_UPDATED_SUCCESSFULLY,
                BookDTO.builder()
                        .title(book.getTitle())
                        .stock(book.getStock())
                        .price(book.getPrice())
                        .build());
    }

    @Override
    public Book findByIdAndCheckStock(final Integer id, final int quantity) {
        return bookRepository.findByIdAndStockGreaterThanEqual(id, quantity);
    }


}
