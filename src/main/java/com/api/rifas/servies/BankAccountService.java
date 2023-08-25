package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.entities.BankAccount;
import com.api.rifas.repositories.BankAccountRepository;
import com.api.rifas.servies.exceptions.DatabaseException;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository repository;
	
	@Transactional
	public List<BankAccount> findAll() {
		return repository.findAll();
	}

	@Transactional
	public BankAccount findById(Long id) {
		Optional<BankAccount> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public BankAccount insert(BankAccount obj) {
	    return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public BankAccount update(Long id, BankAccount obj) {
		try {
			BankAccount entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void updateData(BankAccount entity, BankAccount obj) {
		entity.setName(obj.getName());
		entity.setDocument(obj.getDocument());
		entity.setBankName(obj.getBankName());
		entity.setAccountType(obj.getAccountType());
		entity.setAgency(obj.getAgency());
		entity.setOperation(obj.getOperation());
		entity.setAccountNumber(obj.getAccountNumber());
		entity.setToken(obj.getToken());
	}
}
