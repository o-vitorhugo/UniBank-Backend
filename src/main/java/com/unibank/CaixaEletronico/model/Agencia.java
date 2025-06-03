package com.unibank.CaixaEletronico.model;

import java.util.ArrayList;

public class Agencia {

    private String numeroAgencia;
    private ArrayList<Conta> contas;
    private ArrayList<Cliente> clientes;

    public Agencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
        this.contas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public Cliente buscarCliente(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove pontos e traços do cpf
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve ter 11 dígitos.");
        }
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    public Conta buscarContaETipo(String numeroConta, String tipoConta) {
        for (Conta conta : contas) { // onde contas é a lista de contas da agência
            if (conta.getNumeroConta().equals(numeroConta)) {
                if (tipoConta.equalsIgnoreCase("corrente") && conta instanceof ContaCorrente) {
                    return conta;
                }
                if (tipoConta.equalsIgnoreCase("poupanca") && conta instanceof ContaPoupanca) {
                    return conta;
                }
            }
        }
        return null;
    }

    public Conta buscarConta(String numeroConta) {
        for (Conta c : contas) {
            if (c.getNumeroConta().equals(numeroConta)) {
                return c;
            }
        }
        return null;
    }

    public void aplicarJuros(double taxa) {
        for (Conta c : contas) {
            c.aplicarJuros(taxa);
        }
    }
}