package com.sid.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class AddWalletRquest {

	private String currencyCode;
	private float balance;
}
