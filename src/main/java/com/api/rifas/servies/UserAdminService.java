package com.api.rifas.servies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.rifas.entities.UserAdmin;
import com.api.rifas.repositories.UserAdminRepository;
import com.api.rifas.servies.exceptions.DatabaseException;
import com.api.rifas.servies.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserAdminService {

	@Autowired
	private UserAdminRepository repository;

	@Transactional
	public List<UserAdmin> findAll() {
		return repository.findAll();
	}

	@Transactional
	public UserAdmin findById(Long id) {
		Optional<UserAdmin> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Transactional
	public UserAdmin insert(UserAdmin obj) {
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
	public UserAdmin update(Long id, UserAdmin obj) {
		try {
			UserAdmin entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	public void updateData(UserAdmin entity, UserAdmin obj) {
		entity.setLogin(obj.getLogin());
		entity.setPassword(obj.getPassword());
	}
}