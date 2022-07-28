package com.giovanni.msavaliadordecredito.infra.clients;

import com.giovanni.msavaliadordecredito.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//o FeignClient [e um cliente http para conversar atraves das requisiçoes http
//@FeignClient(url = "http://localhost:8080", path = "/clientes")
@FeignClient(value = "msclientes", path = "/clientes") //mapeando os endpoints
public interface ClienteResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosClient(@RequestParam("cpf") String cpf);

//    @PostMapping
//    public ResponseEntity save(@RequestBody ClienteSaveRequest request);

//    @GetMapping
//    public String status();
}
