package com.api.rifas.resources;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rifas.dto.AuthenticationDTO;
import com.api.rifas.dto.LoginResponseDTO;
import com.api.rifas.dto.RegisterDTO;
import com.api.rifas.entities.UserAdmin;
import com.api.rifas.repositories.UserAdminRepository;
import com.api.rifas.servies.config.TokenService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private UserAdminRepository repository;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		 var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		 var auth = this.authenticationManager.authenticate(usernamePassword);
		 
		 var token = tokenService.generateToken((UserAdmin) auth.getPrincipal());
		 
		 return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
		 if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
		 
		 String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		 UserAdmin newUser = new UserAdmin(null, data.login(), encryptedPassword, data.role());
		 
		 this.repository.save(newUser);

	     return ResponseEntity.ok().build();	 
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid RegisterDTO data){
	    Optional<UserAdmin> optional = repository.findById(id);
	    if(optional.isPresent()){
	        UserAdmin userAdmin = optional.get();
	        // Atualize os campos necessários aqui
	        // Por exemplo, para atualizar o login:
	        userAdmin.setLogin(data.login());
	        // Não esqueça de criptografar a senha antes de definir
	        userAdmin.setPassword(new BCryptPasswordEncoder().encode(data.password()));
	        userAdmin.setRole(data.role());
	        repository.save(userAdmin);
	        return ResponseEntity.ok().build();
	    }else{
	        return ResponseEntity.notFound().build();
	    }
	}
}
