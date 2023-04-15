package com.sid.graphql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.graphql.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, String> {

}
