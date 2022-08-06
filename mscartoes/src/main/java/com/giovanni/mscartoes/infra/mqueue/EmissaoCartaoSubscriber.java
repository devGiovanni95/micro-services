package com.giovanni.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giovanni.mscartoes.domain.Cartao;
import com.giovanni.mscartoes.domain.ClienteCartao;
import com.giovanni.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.giovanni.mscartoes.infra.CartaoRepository;
import com.giovanni.mscartoes.infra.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;


    //Criando metodo para escutar a fila de mensageria
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")//fila que esta escutando
    public void receberSolicitacaoEmissao(@Payload String payload){
       //teste pra ver se estava ouvindo as mensagens
        // System.out.println(payload);

        //vamos pegar os valores desareializando
        var mapper = new ObjectMapper();
        try {
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            //pegando o id do cartao
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            //Salvando os dados do cliente
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

        } catch (JsonProcessingException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }
}
