package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.Conta;
import com.unibank.CaixaEletronico.model.Saque;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saques")
@CrossOrigin("*")
public class SaqueController {

    Agencia agencia = CaixaEletronicoApplication.getAgencia();

    @PostMapping
    public String realizarSaque(@RequestBody SaqueRequest request) {
        String numeroConta = request.getNumeroConta();
        String tipoConta  = request.getTipoConta();
        String senha = request.getSenha();
        double valor = request.getValor();

        Conta conta = agencia.buscarContaETipo(numeroConta, tipoConta);

        if (conta == null) {
            return "Conta não encontrada.";
        }

        if (!conta.getCliente().getSenha().equals(senha)) {
            return "Senha incorreta.";
        }

        double saldoAntes = conta.getSaldo();

        Saque saque = new Saque(conta, valor);
        saque.executar();

        double saldoDepois = conta.getSaldo();

        if (saldoAntes == saldoDepois) {
            return "Saldo insuficiente.";
        }

        return "Saque realizado com sucesso.";
    }

    // DTO interno para receber os dados da requisição
    public static class SaqueRequest {
        private String numeroConta;
        private String tipoConta;
        private String senha;
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

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }
}