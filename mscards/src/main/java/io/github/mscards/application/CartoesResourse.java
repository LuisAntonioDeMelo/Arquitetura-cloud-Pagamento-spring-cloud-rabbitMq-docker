package io.github.mscards.application;

import io.github.mscards.application.representation.CartaoSaveRequest;
import io.github.mscards.application.representation.CartoesPorClientesResponse;
import io.github.mscards.domain.Cartao;
import io.github.mscards.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "cartoes")
@RequiredArgsConstructor
public class CartoesResourse {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartaoRendaMaiorIqual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClientesResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> listaCartoes = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClientesResponse> resultList = listaCartoes.stream()
                .map(CartoesPorClientesResponse::fromModel)
                .toList();

        return ResponseEntity.ok(resultList);
    }


}
