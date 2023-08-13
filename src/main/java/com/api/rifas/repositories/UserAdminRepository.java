package com.api.rifas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rifas.entities.UserAdmin;

public interface UserAdminRepository extends JpaRepository<UserAdmin, Long>{

}
