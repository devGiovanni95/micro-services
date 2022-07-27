package com.giovanni.mscartoes.application.representation;

import com.giovanni.mscartoes.domain.BandeiraCartao;
import com.giovanni.mscartoes.domain.Cartao;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda,limite);
    }
}
