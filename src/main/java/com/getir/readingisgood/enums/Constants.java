package com.getir.readingisgood.enums;

public interface Constants {
    long JWT_EXPIRATION = 900000; //15 mins
    String JWT_SECRET = "prometheus";
    String JWT_TOKEN_TYPE = "Bearer";
    String USERNAME_ALREADY_TAKEN = "Username is already taken !";
    String EMAIL_ALREADY_TAKEN = "Email is already taken !";
    String REGISTER_SUCCESSFUL = "User registered successfully !";
    String LOGIN_FAILED = "Username or password is wrong !";
    String LOGIN_SUCCESFUL = "Login successful !";
    String BOOK_ALREADY_EXISTS = "Book already exists !";
    String BOOK_CREATED_SUCCESSFULLY = "Book created successfully !";
    String BOOK_STOCK_CANT_BE_NEGATIVE = "Stock amount can not be a negative number !";
    String BOOK_NOT_FOUND = "Book not found !";
    String BOOK_STOCK_UPDATED_SUCCESSFULLY = "Book stock updated successfully !";
    String CUSTOMER_NOT_FOUND = "Customer not found !";
    String BOOKS_NOT_FOUND_OR_INSUFFICIENT_QUANTITY = "Book not found or insufficient quantity !";
    String ORDER_QUANTITY_CANT_BE_NEGATIVE = "Order quantity can not be a negative number !";
    String ORDER_CREATED_SUCCESSFULLY = "Order created successfully !";
    String ORDER_NOT_FOUND = "Order not found !";
    String GET_ORDER_SUCCESSFUL = "Get order successful !";
    String GET_STATISTICS_SUCCESSFUL = "Get statistics successful !";
    String STATISTICS_NOT_FOUND = "Statistics not found !";
}
