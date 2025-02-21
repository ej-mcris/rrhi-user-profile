package com.crud.user.profile.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.user.profile.exception.ResourceNotFoundException;
import com.crud.user.profile.model.User;
import com.crud.user.profile.repository.UserRepository;
import com.crud.user.profile.service.UtilityService;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UtilityService service;

	//Create
	@PostMapping("/save")
	public User createUser(@RequestBody User user) {
		user.setAge(service.calculateAge(user.getBday()));
		return userRepository.save(user);
	}
	
	//Read
	@GetMapping("/list")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	//Update
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		User getUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		
		getUser.setName(user.getName());
		getUser.setEmail(user.getEmail());
		getUser.setGender(user.getGender());
		getUser.setBday(user.getBday());
		getUser.setAge(service.calculateAge(user.getBday()));
		getUser.setRole(user.getRole());
		
		User updateUser = userRepository.save(getUser);
		
		return ResponseEntity.ok(updateUser);
	}
	
	//delete
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id){
		User getUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		
		userRepository.delete(getUser);
		
		return ResponseEntity.noContent().build();
	}
	
	//get by id
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id){
		User getUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		
		return ResponseEntity.ok(getUser);
	}
}
