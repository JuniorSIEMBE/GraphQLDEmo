package com.sid.graphql.web;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sid.graphql.dto.AddWalletRquest;
import com.sid.graphql.entities.Currency;
import com.sid.graphql.entities.Wallet;
import com.sid.graphql.repositories.CurrencyRepository;
import com.sid.graphql.repositories.WalletRepository;
import com.sid.graphql.services.WalletService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WalletGraphQLController {
	
	private WalletRepository walletRepository;
	private WalletService walletService;
	private CurrencyRepository currencyRepository;
	
	@QueryMapping
	public List<Wallet> userWallets(){
		return walletRepository.findAll();
	}
	
	@QueryMapping
	public Wallet getWalletbyId(@Argument String id) {
		return walletRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Wallet with id %s not found!", id)));
	}
	
	@MutationMapping
	public Wallet addWallet(@Argument AddWalletRquest wallet) {
		return walletService.save(wallet);
	}

	@QueryMapping
	public List<Currency> currencies(){
		return currencyRepository.findAll();
	}
}
