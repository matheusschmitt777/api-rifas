package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.entities.UserCommission;
import com.api.rifas.repositories.UserCommissionRepository;
import com.api.rifas.servies.exceptions.DatabaseException;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserCommissionService {
	
	@Autowired
	private UserCommissionRepository repository;
	
	@Transactional
	public List<UserCommission> findAll() {
		return repository.findAll();
	}

	@Transactional
	public UserCommission findById(Long id) {
		Optional<UserCommission> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public UserCommission insert(UserCommission obj) {
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
	public UserCommission update(Long id, UserCommission obj) {
		try {
			UserCommission entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void updateData(UserCommission entity, UserCommission obj) {
		entity.setRaffleCommission(obj.getRaffleCommission());
		entity.setUserCommissionStatus(obj.getUserCommissionStatus());
		entity.setSeller(obj.getSeller());
		entity.setPrice(obj.getPrice());
		entity.setPriceCommission(obj.getPriceCommission());
		entity.setAccount(obj.getAccount());
		entity.setActionCommissionStatus(obj.getActionCommissionStatus());
		entity.setLink(obj.getLink());
	}
}
