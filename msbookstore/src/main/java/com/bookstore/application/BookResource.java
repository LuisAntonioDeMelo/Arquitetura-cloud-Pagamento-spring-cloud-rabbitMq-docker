package com.bookstore.application;

import com.bookstore.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookResource {

    private final BookService bookService;
    @GetMapping("/list-actives")
    public ResponseEntity<Book> getAllActiveBooks() {

        return ResponseEntity.ok().build();
    }


}
