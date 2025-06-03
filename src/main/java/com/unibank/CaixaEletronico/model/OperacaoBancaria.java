package com.unibank.CaixaEletronico.model;

public interface OperacaoBancaria {

    void executar(); // Executa as operações
    String exibir(); // Exibe a operação no histórico
}