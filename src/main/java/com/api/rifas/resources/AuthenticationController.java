package com.api.rifas.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rifas.dto.AuthenticationDTO;
import com.api.rifas.dto.RegisterDTO;
import com.api.rifas.entities.UserAdmin;
import com.api.rifas.repositories.UserAdminRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserAdminRepository repository;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		 var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		 var auth = this.authenticationManager.authenticate(usernamePassword);
		 
		 return ResponseEntity.ok().build();
	}
	
	@PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
		 if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
		 
		 String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		 UserAdmin newUser = new UserAdmin(null, data.login(), encryptedPassword, data.role());
		 
		 this.repository.save(newUser);

	     return ResponseEntity.ok().build();	 
	}
}
