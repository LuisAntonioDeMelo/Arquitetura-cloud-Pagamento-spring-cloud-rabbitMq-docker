package com.bookstore.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Entity
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String contactName;

    private String contactEmail;

    private String contactPhone;

    private String address;

    private boolean active;

    private LocalDate registrationDate;

    private LocalDate lastUpdated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_product_suplier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "suplier_id")
    )
    private List<Product> productsSupplied;

}