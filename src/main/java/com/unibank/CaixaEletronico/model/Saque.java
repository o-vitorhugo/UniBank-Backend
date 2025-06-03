package com.unibank.CaixaEletronico.model;

public class Saque implements OperacaoBancaria {

    private Conta conta;
    private double valor;

    public Saque(Conta conta, double valor) {
        this.conta = conta;
        this.valor = valor;
    }

    @Override
    public void executar() {
        if (conta.sacar(valor)) {
            System.out.println("Saque de R$" + valor + " realizado com sucesso.");
            conta.adicionarOperacao(this);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public String exibir() {
        return "Saque de R$ " + String.format("%.2f", valor) +
               " da Conta " + conta.getTipo() + ": "  + conta.getNumeroConta();
    }
}