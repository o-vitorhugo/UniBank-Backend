package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.Cliente;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

    // Salva os clientes em uma lista em memória
    Agencia agencia = CaixaEletronicoApplication.getAgencia();

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente) {
        cliente.setCpf(cliente.getCpf().replaceAll("[^0-9]", "")); // Remove pontos e traços do CPF
        agencia.adicionarCliente(cliente);
        System.out.print("Cliente recebido: " + cliente.getNome() +
                        " | CPF: " + cliente.getCpf() + "\n");
        return ResponseEntity.ok(cliente);
    }
}