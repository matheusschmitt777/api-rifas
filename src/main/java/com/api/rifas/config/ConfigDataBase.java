package com.api.rifas.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.api.rifas.entities.UserAdmin;
import com.api.rifas.entities.enums.UserRole;
import com.api.rifas.repositories.UserAdminRepository;

@Configuration
public class ConfigDataBase implements CommandLineRunner{
	
	@Autowired
	private UserAdminRepository userAdminRepository;
	

	@Override
	public void run(String... args) throws Exception {
		
		UserAdmin ua1 = new UserAdmin(null, "Keçlso329303", "3241214", UserRole.ADMIN);
		userAdminRepository.saveAll(Arrays.asList(ua1));
	}

}
