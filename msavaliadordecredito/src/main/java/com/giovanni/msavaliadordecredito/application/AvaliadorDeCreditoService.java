package com.giovanni.msavaliadordecredito.application;

import com.giovanni.msavaliadordecredito.application.exception.DadosClienteNotFoundException;
import com.giovanni.msavaliadordecredito.application.exception.ErroComunicacaoMicroservicesException;
import com.giovanni.msavaliadordecredito.domain.model.*;
import com.giovanni.msavaliadordecredito.infra.clients.CartoesResourceClient;
import com.giovanni.msavaliadordecredito.infra.clients.ClienteResourceClient;
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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException{

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosClient(cpf);
            ResponseEntity<List< Cartao >> cartoesResource = resourceClient.getCartoesRendaAteh(renda);

            List<Cartao> cartoes = cartoesResource.getBody();
               var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                    DadosCliente dadosCliente = dadosClienteResponse.getBody();

                    BigDecimal limiteBasico = cartao.getLimiteBasico();
                    BigDecimal  rendaBD = BigDecimal.valueOf(renda);
                    BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

                    BigDecimal fator = idadeBD.divide(BigDecimal.valueOf(10));
                    BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                    CartaoAprovado aprovado = new CartaoAprovado();
                    aprovado.setCartao(cartao.getNome());
                    aprovado.setBandeira(cartao.getBandeira());
                    aprovado.setLimiteAprovado(limiteAprovado);

                    return aprovado;
                }).collect(Collectors.toList());

               return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status =  e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }
}
