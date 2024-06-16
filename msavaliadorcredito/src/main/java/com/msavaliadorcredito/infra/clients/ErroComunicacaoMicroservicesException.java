package com.msavaliadorcredito.infra.clients;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception {

    @Getter
    private final Integer status;
    public ErroComunicacaoMicroservicesException(String message, int status) {
        super(message);
        this.status = status;
    }
}
