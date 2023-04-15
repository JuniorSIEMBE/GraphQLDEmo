package com.sid.graphql.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sid.graphql.enums.WalletType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Data
@Entity
public class WalletTransaction {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long timeStamp;
	private float amount;
	@ManyToOne
	private Wallet wallet;
	@Enumerated(EnumType.STRING)
	private WalletType type;
}
