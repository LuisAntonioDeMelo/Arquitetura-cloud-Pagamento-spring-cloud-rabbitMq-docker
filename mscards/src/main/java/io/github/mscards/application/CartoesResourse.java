package io.github.mscards.application;

import io.github.mscards.application.representation.CartaoSaveRequest;
import io.github.mscards.domain.Cartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "cartoes")
@RequiredArgsConstructor
public class CartoesResourse {

    private final CartaoService service;

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda){
        List<Cartao> list = service.getCartaoRendaMaiorIqual(renda);
        return ResponseEntity.ok(list);
    }




}
