package com.bookstore.application;

import com.bookstore.domain.*;
import com.bookstore.application.records.BookRecord;
import com.bookstore.infra.repositories.AuthorRepository;
import com.bookstore.infra.repositories.BookRepository;
import com.bookstore.infra.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        Optional<Publisher> publisher =  publisherRepository.findById(bookRecord.publisherId());
        publisher.ifPresent(book::setPublisher);
        book.setAuthors(new HashSet<>(authorRepository.findAllById(bookRecord.AuthorsIds())));
        Review review = new Review();
        review.setComment(bookRecord.reviewComment());
        review.setBook(book);
        book.setReview(review);
        adjustPriceBase(book);
        return bookRepository.save(book);
    }

    private void adjustPriceBase(Product product) {
        BigDecimal price = product.calculatePriceBasedOnSubProduct();
        product.setSalePrice(price);
    }

    public List<Book> getAllActiveBooks() {
        return bookRepository.findByActiveTrue();
    }

}
