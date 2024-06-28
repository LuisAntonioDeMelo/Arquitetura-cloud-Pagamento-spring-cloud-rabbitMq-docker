package io.github.ls.pagamento.msclientes.application;

import io.github.ls.pagamento.msclientes.application.representation.ClienteSaveRequest;
import io.github.ls.pagamento.msclientes.domain.Address;
import io.github.ls.pagamento.msclientes.domain.AddressBuilder;
import io.github.ls.pagamento.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {


    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request) {

        Cliente  cliente = request.toModel();
        clienteService.save(cliente);

        URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        log.info( "Cliente :: "+ cliente.getCpf() + "foi cadastro: " + LocalDateTime.now());
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping
    public ResponseEntity<Cliente> dadosCliente(@RequestParam("cpf") String cpf) {
        var cliente = clienteService.getByCPF(cpf);

        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
