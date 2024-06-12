package com.msavaliadorcredito.application;

import com.msavaliadorcredito.domain.model.DadosCliente;
import com.msavaliadorcredito.domain.model.SituacaoCliente;
import com.msavaliadorcredito.infra.clients.ClienteResourseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourseClient clienteResourseClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        ResponseEntity<DadosCliente> responseEntity = clienteResourseClient.dadosCliente(cpf);
        return SituacaoCliente.builder()
                .cliente(responseEntity.getBody())
                .build();
    }
}
