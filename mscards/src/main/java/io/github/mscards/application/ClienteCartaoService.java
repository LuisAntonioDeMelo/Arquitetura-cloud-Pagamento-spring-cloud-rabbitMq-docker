package io.github.mscards.application;

import io.github.mscards.domain.ClienteCartao;
import io.github.mscards.infra.repository.ClienteCartaoRepository;

import java.util.List;

public class ClienteCartaoService {

    private ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf) {
        return repository.findAllByCpf(cpf);
    }
}
