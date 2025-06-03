package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/contas")
@CrossOrigin(origins = "*")
public class ContaController {

    @PostMapping
    public Map<String, String> criarConta(@RequestBody CriarContaRequest request) {
        String cpf = request.getCpf();
        String tipoConta = request.getTipoConta();

        Agencia agencia = CaixaEletronicoApplication.getAgencia();
        Cliente cliente = agencia.buscarCliente(cpf);

        if (cliente == null) {
            return Map.of("erro", "Cliente não encontrado.");
        }

        String numeroConta = cliente.getNumeroConta();
        if (numeroConta == null) {
            numeroConta = gerarNumeroConta(cpf);
            cliente.setNumeroConta(numeroConta);
        }

        if (tipoConta.equalsIgnoreCase("corrente")) {
            if (cliente.getContaCorrente() == null) {
                ContaCorrente cc = new ContaCorrente(numeroConta, cliente, agencia);
                cliente.adicionarConta(cc);
                agencia.adicionarConta(cc);
                return Map.of("mensagem", "Conta Corrente criada.", "numeroConta", numeroConta);
            } else {
                return Map.of("erro", "Cliente já possui Conta Corrente.");
            }
        }

        if (tipoConta.equalsIgnoreCase("poupanca")) {
            if (cliente.getContaPoupanca() == null) {
                ContaPoupanca cp = new ContaPoupanca(numeroConta, cliente, agencia);
                cliente.adicionarConta(cp);
                agencia.adicionarConta(cp);
                return Map.of("mensagem", "Conta Poupança criada.", "numeroConta", numeroConta);
            } else {
                return Map.of("erro", "Cliente já possui Conta Poupança.");
            }
        }

        if (tipoConta.equalsIgnoreCase("ambas")) {
            StringBuilder resultado = new StringBuilder();

            if (cliente.getContaCorrente() == null && cliente.getContaPoupanca() == null) {
                ContaCorrente cc = new ContaCorrente(numeroConta, cliente, agencia);
                ContaPoupanca cp = new ContaPoupanca(numeroConta, cliente, agencia);
                cliente.adicionarConta(cc);
                cliente.adicionarConta(cp);
                agencia.adicionarConta(cc);
                agencia.adicionarConta(cp);
                resultado.append("Contas Corrente e Poupança criadas.");
            } else {
                resultado.append("Cliente já possui Conta Corrente ou Poupança.");
            }

            return Map.of("mensagem", resultado.toString(), "numeroConta", numeroConta);
        }

        return Map.of("erro", "Tipo de conta inválido.");

    }

    // Função para gerar o número da conta
    public static String gerarNumeroConta(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove pontos e traços do cpf
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve ter 11 dígitos.");
        }
        String ultimosDigitos = cpf.substring(cpf.length() - 2);
        int aleatorio = new Random().nextInt(9000) + 1000;

        return ultimosDigitos + aleatorio;
    }

    // DTO interno para receber os dados da requisição
    public static class CriarContaRequest {
        private String cpf;
        private String tipoConta;

        // Getters e Setters
        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getTipoConta() {
            return tipoConta;
        }

        public void setTipoConta(String tipoConta) {
            this.tipoConta = tipoConta;
        }
    }
}