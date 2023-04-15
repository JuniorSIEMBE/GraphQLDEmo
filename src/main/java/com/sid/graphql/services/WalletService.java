package com.sid.graphql.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.graphql.dto.AddWalletRquest;
import com.sid.graphql.entities.Currency;
import com.sid.graphql.entities.Wallet;
import com.sid.graphql.entities.WalletTransaction;
import com.sid.graphql.enums.WalletType;
import com.sid.graphql.repositories.CurrencyRepository;
import com.sid.graphql.repositories.WalletRepository;
import com.sid.graphql.repositories.WalletTransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class WalletService {
	
	private CurrencyRepository currencyRepository;
	private WalletRepository walletRepository;
	private WalletTransactionRepository walletTransactionRepository;
	
	public void loadData() throws IOException {
		URI uri = new ClassPathResource("currencies.csv").getURI();
		Path path = Paths.get(uri);
		List<String> lines = Files.readAllLines(path);
		lines.parallelStream().forEach(line ->{
			Currency currency = Currency.builder()
					.code(line.split(";")[0])
					.name(line.split(";")[1])
					//.symbol(line.split(";")[2])
					.salePrice((float) (Math.random()*500)+2)
					.purchasePrice((float) (Math.random()*450)+1)
					.build();
			currencyRepository.save(currency);
		});
		
		Stream.of("CAD","XAF","EUR","USD").forEach(currencyCode ->{
			Currency currency = currencyRepository.findById(currencyCode).orElseThrow(() -> new RuntimeException(String.format("Currency %s not found!", currencyCode)));
			Wallet wallet = Wallet.builder()
					.id(UUID.randomUUID().toString())
					.currency(currency)
					.balance(10000)
					.createdAt(System.currentTimeMillis())
					.userId("user1")
					.build();
			walletRepository.save(wallet);
		});
		
		walletRepository.findAll().forEach(wallet ->{
			for (int i = 0; i < 10; i++) {
				WalletTransaction walletTransactionDebit = WalletTransaction.builder()
						.amount((float) (Math.random()*1000))
						.wallet(wallet)
						.type(WalletType.DEBIT)
						.timeStamp(System.currentTimeMillis())
						.build();
				walletTransactionRepository.save(walletTransactionDebit);
				wallet.setBalance(wallet.getBalance()-walletTransactionDebit.getAmount());
				walletRepository.save(wallet);
				WalletTransaction walletTransactionCredit = WalletTransaction.builder()
						.amount((float) (Math.random()*1000))
						.wallet(wallet)
						.timeStamp(System.currentTimeMillis())
						.type(WalletType.CREDIT)
						.build();
				walletTransactionRepository.save(walletTransactionCredit);
				wallet.setBalance(wallet.getBalance()+walletTransactionCredit.getAmount());
				walletRepository.save(wallet);
			}
		});
	}

	public Wallet save(AddWalletRquest wallet) {
		Currency currency = currencyRepository.findById(wallet.getCurrencyCode()).orElseThrow(() -> new RuntimeException(String.format("Wallet %s not found!", wallet.getCurrencyCode())));
		Wallet walletToSave = Wallet.builder()
				.balance(wallet.getBalance())
				.createdAt(System.currentTimeMillis())
				.currency(currency)
				.userId("user1")
				.id(UUID.randomUUID().toString())
				.build();
		
		return walletRepository.save(walletToSave);
	}
	
	public void walletTransfert(String walletIn, String walletOut, float amount) {
		Wallet in = walletRepository.findById(walletIn).orElseThrow(() -> new RuntimeException(String.format("Wallet %s not found!", walletIn)));
		Wallet out = walletRepository.findById(walletOut).orElseThrow(() -> new RuntimeException(String.format("Wallet %s not found!", walletOut)));

		WalletTransaction transactionIn = WalletTransaction.builder()
				.amount(amount)
				.type(WalletType.DEBIT)
				.wallet(in)
				.timeStamp(System.currentTimeMillis())
				.build();
		float amountDestination = amount + (out.getCurrency().getSalePrice()/in.getCurrency().getPurchasePrice());
		WalletTransaction transactionOut = WalletTransaction.builder()
				.amount(amountDestination)
				.type(WalletType.CREDIT)
				.wallet(out)
				.timeStamp(System.currentTimeMillis())
				.build();
		walletTransactionRepository.save(transactionIn);
		walletTransactionRepository.save(transactionOut);
		in.setBalance(in.getBalance()-amount);
		out.setBalance(out.getBalance()+amountDestination);
		walletRepository.save(in);
		walletRepository.save(out);
	}

}
