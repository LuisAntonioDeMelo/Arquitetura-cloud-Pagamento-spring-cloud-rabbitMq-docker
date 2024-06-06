package io.github.ls.pagamento.msclientes.domain;

import jakarta.persistence.*;

import java.util.Locale;
import java.util.UUID;
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String street;
    private String complement;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    protected Address() {
    }

    public Address(String street, String complement) {
        this.street = street;
        this.complement = complement;
    }

    protected String getStreet() {
        return street;
    }

    protected void setStreet(String street) {
        this.street = street;
    }

    protected String getComplement() {
        return complement;
    }

    protected void setComplement(String complement) {
        this.complement = complement;
    }
}
