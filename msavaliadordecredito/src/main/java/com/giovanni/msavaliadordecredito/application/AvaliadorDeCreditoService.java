package com.giovanni.msavaliadordecredito.application;

import com.giovanni.msavaliadordecredito.domain.model.CartaoCliente;
import com.giovanni.msavaliadordecredito.domain.model.DadosCliente;
import com.giovanni.msavaliadordecredito.domain.model.SituacaoCliente;
import com.giovanni.msavaliadordecredito.infra.clients.CartoesResourceClient;
import com.giovanni.msavaliadordecredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorDeCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient resourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        //obter dados do cliente   //MSCLIENTES
        //obter cartoes do cliente //MSCARTOES

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClient(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse =resourceClient.getCartoesByCliente(cpf);

        //pegar os dados do cliente
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();


    }
}
