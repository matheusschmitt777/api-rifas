package com.api.rifas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rifas.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

}
