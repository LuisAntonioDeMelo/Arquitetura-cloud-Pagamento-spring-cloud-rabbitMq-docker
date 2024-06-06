package io.github.ls.pagamento.msclientes.domain;

public class AddressBuilder {
    //só pra lembrar como faz o builder
    private Address address;

    private AddressBuilder(){
        address = new Address();
    }

    public static AddressBuilder builder(){
        return new AddressBuilder();
    }

    public AddressBuilder street(String street){
        this.address.setStreet(street);
        return this;
    }

    public AddressBuilder complement(String complement){
        this.address.setComplement(complement);
        return this;
    }

    public Address build() {
        return this.address;
    }
}
