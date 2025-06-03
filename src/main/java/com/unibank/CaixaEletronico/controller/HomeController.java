package com.unibank.CaixaEletronico.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Anotação que indica que a classe se trata de um controller (responde requisições HTTP)
@RequestMapping("/") // Define o caminho base do controller (no caso, a raiz do servidor)
@CrossOrigin("*") // Permite que o frontend consiga acessar esse endpoint
public class HomeController {
    // Inicia o servidor
    @GetMapping
    public String home(){
        return "Servidor Rodando!";
    }
}