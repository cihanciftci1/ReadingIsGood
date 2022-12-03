package com.getir.readingisgood.service.book;

import com.getir.readingisgood.dto.BookDTO;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.book.CreateBookRequest;
import com.getir.readingisgood.model.request.book.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.BaseResponse;
import com.getir.readingisgood.model.response.book.CreateBookResponse;
import com.getir.readingisgood.model.response.book.UpdateBookStockResponse;
import com.getir.readingisgood.repository.book.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImp bookService;

    @Test
    void book_service_create_should_return_created_book(){
        //given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .title("title")
                .price(1.0)
                .stock(1)
                .build();

        Book book = Book.builder()
                .id(1)
                .title("title")
                .price(1.0)
                .stock(1)
                .build();

        CreateBookResponse expectedResponse = new CreateBookResponse(Constants.BOOK_CREATED_SUCCESSFULLY,
                BookDTO.builder()
                        .title("title")
                        .price(1.0)
                        .stock(1)
                        .build());

        //when
        when(bookRepository.existsByTitle(any(String.class))).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BaseResponse actualResponse = bookService.create(createBookRequest);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());
    }

    @Test
    void book_service_updateStock_should_return_updated_book(){
        //given
        UpdateBookStockRequest updateBookStockRequest = UpdateBookStockRequest.builder()
                .bookId(1)
                .stock(1)
                .build();

        Optional<Book> book = Optional.of(Book.builder()
                .id(1)
                .title("title")
                .price(1.0)
                .stock(1)
                .build());

        UpdateBookStockResponse expectedResponse = new UpdateBookStockResponse(Constants.BOOK_STOCK_UPDATED_SUCCESSFULLY,
                BookDTO.builder()
                        .title("title")
                        .price(1.0)
                        .stock(1)
                        .build());


        //when
        when(bookRepository.existsById(any(Integer.class))).thenReturn(true);
        when(bookRepository.findById(any(Integer.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book.get());
        BaseResponse actualResponse = bookService.updateStock(updateBookStockRequest);

        //then
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getMessage()).isEqualTo(expectedResponse.getMessage());
    }

}