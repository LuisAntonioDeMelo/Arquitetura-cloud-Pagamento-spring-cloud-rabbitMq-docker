package com.bookstore.infra.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(){
        super("Nenhum livro foi encontrado na loja");
    }
}
