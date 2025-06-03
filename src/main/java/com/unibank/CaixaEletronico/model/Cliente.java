package com.unibank.CaixaEletronico.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente {

    @NotBlank
    private String nome;
    @NotBlank @CPF
    private String cpf;
    @NotBlank
    private String senha;
    @NotNull
    private LocalDate dataNascimento;
    private String numeroConta;

    private ArrayList<Conta> contas;

    public Cliente(String nome, String cpf, String senha, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public boolean validarSenha(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setCpf(@NotBlank @CPF String cpf) {
        this.cpf = cpf;
    }

    public void setSenha(@NotBlank String senha) {
        this.senha = senha;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Conta getContaCorrente() {
        for (Conta conta : contas) {
            if (conta instanceof ContaCorrente) return conta;
        }
        return null;
    }

    public Conta getContaPoupanca() {
        for (Conta conta : contas) {
            if (conta instanceof ContaPoupanca) return conta;
        }
        return null;
    }
}