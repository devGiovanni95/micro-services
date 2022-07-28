package com.giovanni.msavaliadordecredito.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorDeCreditoResource {

    @GetMapping
    public String status(){
        return "ok";
    }
}
