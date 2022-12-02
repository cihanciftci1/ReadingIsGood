package com.getir.readingisgood.enums;

public interface Constants {
    long JWT_EXPIRATION = 700000;
    String JWT_SECRET = "prometheus";
    String JWT_TOKEN_TYPE = "Bearer";
    String USERNAME_ALREADY_TAKEN = "Username is already taken !";
    String EMAIL_ALREADY_TAKEN = "Email is already taken !";
    String REGISTER_SUCCESSFUL = "User registered successfully !";
    String LOGIN_FAILED = "Username or password is wrong !";
    String LOGIN_SUCCESFULL = "Login successful !";
    String BOOK_ALREADY_EXISTS = "Book already exists !";
    String BOOK_CREATED_SUCCESSFULLY = "Book created successfully !";
    String BOOK_STOCK_CANT_BE_NEGATIVE = "Stock amount can not be a negative number !";
    String BOOK_NOT_FOUND = "Book not found !";
    String BOOK_STOCK_UPDATED_SUCCESFULLY = "Book stock updated successfully !";
}
