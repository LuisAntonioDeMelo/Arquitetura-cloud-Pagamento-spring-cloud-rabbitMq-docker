package com.bookstore.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "TB_BOOK")
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private UUID uiud;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private Review review;

    private Integer bestSellerRate;

    @Override
    public BigDecimal calculatePriceBasedOnSubProduct() {
        if(getStockQuantity() > 20) {
            getRate();
            return salePrice.multiply(interestRate.subtract(BigDecimal.valueOf(0.02))); //desconto por ser livros
        }
       return super.calculatePriceBasedOnSubProduct();
    }


    private void getRate() {
        switch (bestSellerRate) {
            case 1 -> setInterestRate(BigDecimal.valueOf(1));
            case 2 -> setInterestRate(BigDecimal.valueOf(2));
            case 3 -> setInterestRate(BigDecimal.valueOf(3));
           default -> {
           }
       };
    }
}
