package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.Conta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/saldo")
@CrossOrigin("*")
public class SaldoController {

    Agencia agencia = CaixaEletronicoApplication.getAgencia();

    @PostMapping
    public ResponseEntity<?> consultarSaldo(@RequestBody SaldoRequest request) {
        try {
            String numeroConta = request.getNumeroConta();
            String tipoConta = request.getTipoConta();
            String senha = request.getSenha();

            Conta conta = agencia.buscarContaETipo(numeroConta, tipoConta);
            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada");
            }
            if(!conta.getCliente().validarSenha(senha)) {
                throw new IllegalArgumentException("Senha inválida");
            }

            double saldo = conta.getSaldo();

            // Retorna JSON com o saldo
            return ResponseEntity.ok(Map.of("valor", saldo));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro interno no servidor"));
        }
    }

    // DTO interno para receber os dados da requisição
    public static class SaldoRequest {
        private String numeroConta;
        private String tipoConta;
        private String senha;

        // Getters e Setters
        public String getNumeroConta() {
            return numeroConta;
        }

        public void setNumeroConta(String numeroConta) {
            this.numeroConta = numeroConta;
        }

        public String getTipoConta() {
            return tipoConta;
        }

        public void setTipoConta(String tipoConta) {
            this.tipoConta = tipoConta;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
