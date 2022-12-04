package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.dto.BookDTO;
import com.getir.readingisgood.enums.Constants;
import com.getir.readingisgood.model.request.book.CreateBookRequest;
import com.getir.readingisgood.model.request.book.UpdateBookStockRequest;
import com.getir.readingisgood.model.response.book.CreateBookResponse;
import com.getir.readingisgood.model.response.book.UpdateBookStockResponse;
import com.getir.readingisgood.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void book_controller_create_book_should_return_created_book() throws Exception{
        //given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
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
        when(bookService.create(createBookRequest)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createBookRequest)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.data.book.title", is(expectedResponse.getData().get("book").getTitle())));

    }

    @Test
    void book_controller_update_book_stock_should_return_created_book() throws Exception{
        //given
        UpdateBookStockRequest updateBookStockRequest = UpdateBookStockRequest.builder()
                .bookId(1)
                .stock(1)
                .build();

        UpdateBookStockResponse expectedResponse = new UpdateBookStockResponse(Constants.BOOK_CREATED_SUCCESSFULLY,
                BookDTO.builder()
                        .title("title")
                        .price(1.0)
                        .stock(1)
                        .build());

        //when
        when(bookService.updateStock(updateBookStockRequest)).thenReturn(expectedResponse);
        final ResultActions actions = mockMvc.perform(post("/book/updateStock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookStockRequest)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponse.getMessage())))
                .andExpect(jsonPath("$.data.book.title", is(expectedResponse.getData().get("book").getTitle())));


    }

}