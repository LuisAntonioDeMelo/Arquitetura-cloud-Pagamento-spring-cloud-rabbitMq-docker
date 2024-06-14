package com.msavaliadorcredito.application;

import com.msavaliadorcredito.domain.model.CartaoCliente;
import com.msavaliadorcredito.domain.model.DadosCliente;
import com.msavaliadorcredito.domain.model.SituacaoCliente;
import com.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.msavaliadorcredito.infra.clients.ClienteResourseClient;
import com.msavaliadorcredito.infra.clients.DadosClienteNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourseClient clienteResourseClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourseClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }
        catch (FeignException.FeignClientException e) {
            throw new DadosClienteNotFoundException();
        }
    }
}
