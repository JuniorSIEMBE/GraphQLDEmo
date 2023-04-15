package com.sid.graphql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Data
@Entity
public class Currency {
	
	@Id
	private String code;
	private String name;
	private String symbol;
	private float salePrice;
	private float purchasePrice;
}
