package com.sid.graphql.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Data
@Entity
public class Wallet {

	@Id
	private String id;
	private float balance;
	private Long createdAt;
	private String userId;
	@ManyToOne
	private Currency currency;
	@OneToMany(mappedBy = "wallet")
	private List<WalletTransaction> transactions;
}
