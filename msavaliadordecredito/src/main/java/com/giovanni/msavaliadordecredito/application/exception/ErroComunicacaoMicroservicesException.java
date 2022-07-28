package com.giovanni.msavaliadordecredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception{

    @Getter//obter status
    private Integer status;

    public ErroComunicacaoMicroservicesException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
