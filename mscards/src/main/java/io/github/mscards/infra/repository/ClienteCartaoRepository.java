package io.github.mscards.infra.repository;

import io.github.mscards.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {


    List<ClienteCartao> findAllByCpf(String cpf);
}
