package com.sid.graphql;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sid.graphql.services.WalletService;

@SpringBootApplication
public class GraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(WalletService walletService) {
		return args ->{
			walletService.loadData();
		};
	}

}
