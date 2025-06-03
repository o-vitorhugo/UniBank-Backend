package com.unibank.CaixaEletronico.model;

public class Transferencia implements OperacaoBancaria {

    private Conta conta;
    private Conta destino;
    private double valor;

    public Transferencia(Conta conta, Conta destino, double valor) {
        this.conta = conta;
        this.destino = destino;
        this.valor = valor;
    }

    @Override
    public void executar() {
        if (conta.transferir(valor, destino)) {
            System.out.println("Transferência de R$" + valor +
                               " realizada para a conta " + destino.getNumeroConta() +
                               " - " + destino.getCliente().getNome());
            conta.adicionarOperacao(this);
        } else {
            System.out.println("Saldo insuficiente para transferência.");
        }
    }

    @Override
    public String exibir() {
        return "Transferência de R$ " + String.format("%.2f", valor) +
               " da Conta " + conta.getTipo() + ": "  + conta.getNumeroConta() +
               " para a conta " + destino.getNumeroConta();
    }
}