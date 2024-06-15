package com.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode
public abstract class Product {
    private static final Integer LOW_STOCK_THRESHOLD = 10;
    private static final Double COST_MORE = 1.25;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal purchasePrice;

    protected BigDecimal salePrice;

    private Integer stockQuantity;

    private boolean active;

    private LocalDate registrationDate;

    private Integer productCode;

    private String description;

    protected BigDecimal interestRate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "productsSupplied", fetch = FetchType.LAZY)
    private List<Supplier> suppliers;

    public BigDecimal calculatePriceBasedOnSubProduct() {
         if(stockQuantity < LOW_STOCK_THRESHOLD){
            return salePrice.add(BigDecimal.valueOf(COST_MORE)).multiply(interestRate); //se o estoque é menor o preço sobe
        }
        return salePrice.multiply(interestRate) ;
    }

}
