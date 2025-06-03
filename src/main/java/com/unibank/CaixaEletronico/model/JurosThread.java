package com.unibank.CaixaEletronico.model;

public class JurosThread extends Thread {

    private Agencia agencia;
    private double taxa;

    public JurosThread(Agencia agencia, double taxa) {
        this.agencia = agencia;
        this.taxa = taxa;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(10000); // 1 minuto
                agencia.aplicarJuros(taxa);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread de juros interrompida.");
        }
    }
}