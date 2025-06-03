package com.unibank.CaixaEletronico.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    protected String numeroConta;
    protected String codigoOperacao;
    protected Cliente cliente;
    protected Agencia agencia;
    protected double saldo;
    protected ArrayList<OperacaoBancaria> historico;

    public Conta(String numeroConta, String codigoOperacao, Cliente cliente, Agencia agencia) {
        this.numeroConta = numeroConta;
        this.codigoOperacao = codigoOperacao;
        this.cliente = cliente;
        this.agencia = agencia;
        this.saldo = 0.0;
        this.historico = new ArrayList<>();
    }

    public abstract String getTipo();

    public boolean depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            return true;
        }
        return false;
    }

    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public boolean transferir(double valor, Conta destino) {
        if (sacar(valor)) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public void adicionarOperacao(OperacaoBancaria op) {
        historico.add(op);
    }

    public List<OperacaoBancaria> getHistorico() {
        return historico;
    }

    public abstract void aplicarJuros(double taxa); // Método abstrato, que será aplicado em ContaPoupanca

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }
}