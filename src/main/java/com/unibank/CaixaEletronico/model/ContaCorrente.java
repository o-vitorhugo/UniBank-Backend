package com.unibank.CaixaEletronico.model;

public class ContaCorrente extends Conta {

    public ContaCorrente(String numeroConta, Cliente cliente, Agencia agencia) {
        super(numeroConta, "001", cliente, agencia);
    }

    @Override
    public String getTipo() {
        return "Corrente";
    }

    @Override
    public void aplicarJuros(double taxa) {
        // Método vazio, pois juros são aplicados apenas na Conta Poupança
    }
}