package com.br.emakers.apiProjeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiProjetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProjetoApplication.class, args);
	}

}
