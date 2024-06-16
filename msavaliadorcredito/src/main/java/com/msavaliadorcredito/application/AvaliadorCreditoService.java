package com.msavaliadorcredito.application;

import com.msavaliadorcredito.domain.model.*;
import com.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.msavaliadorcredito.infra.clients.ClienteResourseClient;
import com.msavaliadorcredito.infra.clients.DadosClienteNotFoundException;
import com.msavaliadorcredito.infra.clients.ErroComunicacaoMicroservicesException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourseClient clienteResourseClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourseClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesResourceClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            if (HttpStatus.NOT_FOUND.value() == e.status()) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), e.status());
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(DadosAvaliacao dadosAvaliacao)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourseClient
                    .dadosCliente(dadosAvaliacao.getCpf());

            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient
                    .getCartoesRendaAteh(dadosAvaliacao.getRenda());

            List<Cartao> cartoes = cartoesResponse.getBody();

            List<CartaoAprovado> cartoesAprovados = cartoes.stream().map(cartao -> {
                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                cartaoAprovado.setNome(cartaoAprovado.getNome());
                cartaoAprovado.setBandeira(cartao.getBandeira());

                BigDecimal limiteAprovado = getLimiteAprovado(dadosClienteResponse, cartao);
                cartaoAprovado.setLimiteAprovado(limiteAprovado);

                return cartaoAprovado;
            }).toList();
            return new RetornoAvaliacaoCliente(cartoesAprovados);
        }
        catch (FeignException.FeignClientException e) {
            if (HttpStatus.NOT_FOUND.value() == e.status()) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), e.status());
        }
    }

    private BigDecimal getLimiteAprovado(ResponseEntity<DadosCliente> dadosClienteResponse, Cartao cartao) {
        DadosCliente dadosCliente =  dadosClienteResponse.getBody();

        BigDecimal limiteBasico = cartao.getLimiteBasico();
        BigDecimal idadeBd = BigDecimal.valueOf(dadosCliente.getIdade());

        BigDecimal fator = idadeBd.divide(BigDecimal.valueOf(10));
        BigDecimal limiteAprovado = fator.multiply(limiteBasico);
        return limiteAprovado;
    }
}
