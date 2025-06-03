package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.Conta;
import com.unibank.CaixaEletronico.model.Deposito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/depositos")
@CrossOrigin("*")
public class DepositoController {

    Agencia agencia = CaixaEletronicoApplication.getAgencia();

    @PostMapping
    public ResponseEntity<?> realizarDeposito(@RequestBody DepositoRequest request) {
        try {
            String numeroConta = request.getNumeroConta();
            String tipoConta = request.getTipoConta();
            double valor = request.getValor();

            Conta conta = agencia.buscarContaETipo(numeroConta, tipoConta);
            if (conta == null) {
                throw new IllegalArgumentException("Conta não encontrada");
            }

            if (valor <= 0) {
                throw new IllegalArgumentException("O valor do depósito deve ser maior que zero");
            }

            Deposito deposito = new Deposito(conta, valor);
            deposito.executar();
            return ResponseEntity.ok(Map.of("mensagem", "Depósito realizado com sucesso."));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro interno no servidor"));
        }
    }

    // DTO interno para receber os dados da requisição
    public static class DepositoRequest {
        private String numeroConta;
        private String tipoConta;
        private double valor;

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

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }
}