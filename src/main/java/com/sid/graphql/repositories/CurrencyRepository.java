package com.sid.graphql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.graphql.entities.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String>{

}
