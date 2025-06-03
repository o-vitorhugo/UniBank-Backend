package com.unibank.CaixaEletronico;

import com.unibank.CaixaEletronico.model.Agencia;
import com.unibank.CaixaEletronico.model.JurosThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaixaEletronicoApplication {

	private static final Agencia agencia = new Agencia("001");

	public static Agencia getAgencia() {
		return agencia;
	}

	public static void main(String[] args) {
		SpringApplication.run(CaixaEletronicoApplication.class, args);

		// Iniciando a thread de juros com taxa de 1.25% (0.0125)
        JurosThread jurosThread = new JurosThread(agencia, 0.0125);
        jurosThread.start();
	}
}