package com.giovanni.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscriber {

    //Criando metodo para escutar a fila de mensageria
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")//fila que esta escutando
    public void receberSolicitacaoEmissao(@Payload String payload){
        System.out.println(payload);
    }
}
