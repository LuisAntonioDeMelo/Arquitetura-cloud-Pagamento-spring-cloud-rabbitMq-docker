package io.github.mscards.application.representation;

import io.github.mscards.domain.ClienteCartao;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartoesPorClientesResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClientesResponse fromModel(ClienteCartao model) {
        return CartoesPorClientesResponse.builder()
                .nome(model.getCartao().getNome())
                .bandeira(model.getCartao().getBandeira().toString())
                .limiteLiberado(model.getCartao().getLimiteBasico())
                .build();

    }
}
