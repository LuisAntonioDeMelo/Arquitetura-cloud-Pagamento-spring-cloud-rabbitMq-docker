package com.bookstore.application.services;

import com.bookstore.domain.Book;
import com.bookstore.domain.Review;
import com.bookstore.application.services.records.BookRecord;
import com.bookstore.infra.repositories.AuthorRepository;
import com.bookstore.infra.repositories.BookRepository;
import com.bookstore.infra.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public Book saveBook(BookRecord bookRecord) {
        Book book = new Book();
        book.setTitle(book.getTitle());
        book.setPublisher(publisherRepository.findById(bookRecord.publisherId()).get());
        book.setAuthors(authorRepository.findAllById(bookRecord.AuthorsIds()).stream().collect(Collectors.toSet()));

        Review review = new Review();
        review.setComment(bookRecord.reviewComment());
        review.setBook(book);
        book.setReview(review);

        return bookRepository.save(book);
    }

}
