package com.unibank.CaixaEletronico.model;

public class Deposito implements OperacaoBancaria {

    private Conta conta;
    private double valor;

    public Deposito(Conta conta, double valor) {
        this.conta = conta;
        this.valor = valor;
    }

    @Override
    public void executar() {
        try {
            if (conta.depositar(valor)) {
                System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
                conta.adicionarOperacao(this);
            } else {
                System.out.println("Valor inválido para depósito.");
            }
        } catch (Exception e) {
            System.out.println("Erro no depósito: " + e.getMessage());
            throw e;  // relança para ser tratado no controller
        }
    }

    @Override
    public String exibir() {
        return "Depósito de R$ " + String.format("%.2f", valor) +
               " na Conta " + conta.getTipo() + ": "  + conta.getNumeroConta();
    }
}