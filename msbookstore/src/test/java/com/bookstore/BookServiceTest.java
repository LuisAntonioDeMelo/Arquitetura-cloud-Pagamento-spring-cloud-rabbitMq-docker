package com.bookstore;

import com.bookstore.application.BookService;
import com.bookstore.domain.Book;
import com.bookstore.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    private Book book;
    public BookServiceTest() {

    }


    @Test
    public void deveSalvarLivroECalcularPreco() {
        criarLivro();
        processarPreco(book);
        verificarCalculo(book);

    }


    private void criarLivro() {
        book = new Book();
        book.setInterestRate(BigDecimal.valueOf(0.2));
        book.setBestSellerRate(1);
        book.setSalePrice(BigDecimal.valueOf(30.0));
        book.setStockQuantity(40);

    }
    public void processarPreco(Product product) {
        BigDecimal price = product.calculatePriceBasedOnSubProduct();
        product.setSalePrice(price);
    }
    private void verificarCalculo(Product product) {
        System.out.println(product.getSalePrice());
        Assertions.assertEquals("29.400" , product.getSalePrice().toString());
    }
}
