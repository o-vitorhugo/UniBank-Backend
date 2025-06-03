package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.Conta;
import com.unibank.CaixaEletronico.model.OperacaoBancaria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/historico")
@CrossOrigin("*")
public class HistoricoController {

    Agencia agencia = CaixaEletronicoApplication.getAgencia();

    @PostMapping
    public ResponseEntity<?> consultarHistorico(@RequestBody HistoricoRequest request) {
        try {
            String numeroConta = request.getNumeroConta();
            String senha = request.getSenha();

            Conta conta = agencia.buscarConta(numeroConta);

            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada");
            }

            if(!conta.getCliente().validarSenha(senha)) {
                throw new IllegalArgumentException("Senha inválida");
            }

            List<String> historico = new ArrayList<>();
            for (OperacaoBancaria op : conta.getHistorico()) {
                historico.add(op.exibir());
            }

            return ResponseEntity.ok(historico);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro interno no servidor"));
        }
    }

    // DTO interno para receber os dados da requisição
    public static class HistoricoRequest {
        private String numeroConta;
        private String senha;

        // Getters e Setters
        public String getNumeroConta() {
            return numeroConta;
        }

        public void setNumeroConta(String numeroConta) {
            this.numeroConta = numeroConta;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}