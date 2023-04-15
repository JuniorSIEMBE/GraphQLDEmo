package com.sid.graphql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.graphql.entities.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

}
