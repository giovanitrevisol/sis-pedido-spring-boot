package com.giovanitrevisol.sispedido;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SispedidoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SispedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}

//testar o swagger
//Testar: http://localhost:8080/swagger-ui.html
