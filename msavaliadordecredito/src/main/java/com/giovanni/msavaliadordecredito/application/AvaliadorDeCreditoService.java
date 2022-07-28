package com.giovanni.msavaliadordecredito.application;

import com.giovanni.msavaliadordecredito.domain.model.DadosCliente;
import com.giovanni.msavaliadordecredito.domain.model.SituacaoCliente;
import com.giovanni.msavaliadordecredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorDeCreditoService {

    private final ClienteResourceClient clientesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        //obter dados do cliente   //MSCLIENTES
        //obter cartoes do cliente //MSCARTOES

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClient(cpf);

        //pegar os dados do cliente
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();


    }
}
