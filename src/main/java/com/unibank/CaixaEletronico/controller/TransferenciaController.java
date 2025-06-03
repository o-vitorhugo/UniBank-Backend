package com.unibank.CaixaEletronico.controller;

import com.unibank.CaixaEletronico.CaixaEletronicoApplication;
import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.Conta;
import com.unibank.CaixaEletronico.model.Transferencia;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transferencias")
@CrossOrigin("*")
public class TransferenciaController {

    Agencia agencia = CaixaEletronicoApplication.getAgencia();

    @PostMapping
    public String realizarTransferencia(@RequestBody TransferenciaRequest request) {
        try {
            String numeroContaOrigem = request.getNumeroContaOrigem();
            String tipoConta = request.getTipoConta();
            String senha = request.getSenha();
            String numeroContaDestino = request.getNumeroContaDestino();
            double valor = request.getValor();

            Conta contaOrigem = agencia.buscarContaETipo(numeroContaOrigem, tipoConta);

            if (contaOrigem == null) {
                return "Conta de origem não encontrada.";
            }

            if (!contaOrigem.getCliente().getSenha().equals(senha)) {
                return "Senha incorreta.";
            }

            if (contaOrigem.getSaldo() < valor) {
                return "Saldo insuficiente para realizar a transferência.";
            }

            // Descobrir tipo da conta destino automaticamente
            Conta contaDestino = agencia.buscarContaETipo(numeroContaDestino, "corrente");
            if (contaDestino == null) {
                contaDestino = agencia.buscarContaETipo(numeroContaDestino, "poupanca");
            }

            if (contaDestino == null) {
                return "Conta de destino não encontrada.";
            }

            if (contaOrigem.getSaldo() < valor) {
                return "Saldo insuficiente para realizar a transferência.";
            }

            Transferencia transferencia = new Transferencia(contaOrigem, contaDestino, valor);
            transferencia.executar();

            return "Transferência realizada com sucesso!";
        } catch (Exception e) {
            return "Erro ao processar a transferência: " + e.getMessage();
        }
    }

    // DTO interno para receber os dados da requisição
    public static class TransferenciaRequest {
        private String numeroContaOrigem;
        private String tipoConta;
        private String senha;
        private String numeroContaDestino;
        private double valor;

        // Getters e Setters
        public String getNumeroContaOrigem() {
            return numeroContaOrigem;
        }

        public void setNumeroContaOrigem(String numeroContaOrigem) {
            this.numeroContaOrigem = numeroContaOrigem;
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

        public String getNumeroContaDestino() {
            return numeroContaDestino;
        }

        public void setNumeroContaDestino(String numeroContaDestino) {
            this.numeroContaDestino = numeroContaDestino;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }
}