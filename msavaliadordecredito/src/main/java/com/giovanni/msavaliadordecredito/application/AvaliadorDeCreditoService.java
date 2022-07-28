package com.giovanni.msavaliadordecredito.application;

import com.giovanni.msavaliadordecredito.application.exception.DadosClienteNotFoundException;
import com.giovanni.msavaliadordecredito.application.exception.ErroComunicacaoMicroservicesException;
import com.giovanni.msavaliadordecredito.domain.model.CartaoCliente;
import com.giovanni.msavaliadordecredito.domain.model.DadosCliente;
import com.giovanni.msavaliadordecredito.domain.model.SituacaoCliente;
import com.giovanni.msavaliadordecredito.infra.clients.CartoesResourceClient;
import com.giovanni.msavaliadordecredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorDeCreditoService{

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient resourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{
        //obter dados do cliente   //MSCLIENTES
        //obter cartoes do cliente //MSCARTOES


        try {

            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClient(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = resourceClient.getCartoesByCliente(cpf);

            //pegar os dados do cliente
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();


        }catch (FeignException.FeignClientException e){
            int status =  e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }


    }
}
