package com.giovanni.msavaliadordecredito.application;

import com.giovanni.msavaliadordecredito.application.exception.DadosClienteNotFoundException;
import com.giovanni.msavaliadordecredito.application.exception.ErroComunicacaoMicroservicesException;
import com.giovanni.msavaliadordecredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorDeCreditoResource {

    private final AvaliadorDeCreditoService avaliadorDeCreditoService;
    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity/*<SituacaoCliente>*/ consultaSituacaoCliente(@RequestParam("cpf") String cpf){

        try {
            SituacaoCliente situacaoCliente = avaliadorDeCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }
}
