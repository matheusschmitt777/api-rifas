package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.entities.Raffle;
import com.api.rifas.repositories.RaffleRepository;
import com.api.rifas.servies.exceptions.DatabaseException;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RaffleService {

	@Autowired
	private RaffleRepository repository;

	@Transactional
	public List<Raffle> findAll(){
		return repository.findAll();
	} 

	@Transactional
	public Raffle findById(Long id) {
		Optional<Raffle> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Transactional
	public Raffle insert(Raffle obj) {
		return repository.save(obj);
	}
	
	@Transactional
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
	public Raffle update(Long id, Raffle obj) {
		try {
			Raffle entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void updateData(Raffle entity, Raffle obj) {
		entity.setQuantity(obj.getQuantity());
		entity.setName(obj.getName());
		entity.setDescription(obj.getDescription());
		entity.setPrice(obj.getPrice());
		entity.setImgUrl(obj.getImgUrl());
	}
}
