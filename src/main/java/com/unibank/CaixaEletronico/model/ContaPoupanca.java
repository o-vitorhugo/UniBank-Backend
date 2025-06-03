package com.unibank.CaixaEletronico.model;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String numeroConta, Cliente cliente, Agencia agencia) {
        super(numeroConta, "013", cliente, agencia);
    }

    @Override
    public String getTipo() {
        return "Poupança";
    }

    @Override
    public void aplicarJuros(double taxa) {
        saldo += saldo * taxa;
    }
}