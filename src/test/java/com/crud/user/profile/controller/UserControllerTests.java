package com.crud.user.profile.controller;


import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.crud.user.profile.model.User;
import com.crud.user.profile.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserControllerTests {
    @Autowired
    private UserController controller;
    
    @DisplayName("Create User Profile")
    @Test
    void createUser() throws Exception {
    	User user = new User();
    	
		user.setName("Juan Carlo Miguel");
    	user.setEmail("jcmiguel@gmail.com");
    	user.setGender("male");
    	user.setBday("1991-07-08");
    	user.setAge(calculateAge(user.getBday()));
		user.setRole("Java Programmer");
		
		Assertions.assertTrue(controller.getAllUsers().size() > 0);
    }
    
    @DisplayName("Read user profile")
    @Test
    void readUser() throws Exception {
    	User user = new User();
    	
    	user.setId(2L);
		user.setName("Dos Carlo Miguel");
    	user.setEmail("jcmiguel@gmail.com");
    	user.setGender("male");
    	user.setBday("1991-07-08");
    	user.setAge(calculateAge(user.getBday()));
		user.setRole("Java Programmer");
		controller.createUser(user);
		
    	ResponseEntity<User> getUser = controller.getUser(2L);
    	
    	Assertions.assertSame(getUser.getBody().getId(), user.getId());
    }
    
    @DisplayName("Update user profile")
    @Test
    void updateuser() throws Exception {
    	String expectedRole = "Java Programmer";
    	User user = new User();
    	
		user.setName("Tres Carlo Miguel");
    	user.setEmail("jcmiguel@gmail.com");
    	user.setGender("male");
    	user.setBday("1991-07-08");
    	user.setAge(calculateAge(user.getBday()));
    	user.setRole("Senior Java Programmer");
    	controller.createUser(user);
    	
		String checkRole = controller.updateUser(3L, user).getBody().getRole();
		
		Assertions.assertNotEquals(expectedRole, checkRole);
    }
    
    @DisplayName("Delete user profile")
    @Test
    void deleteuser() throws Exception {
    	User user = new User();
    	
		user.setName("Thirdy Ravena");
    	user.setEmail("travena@gmail.com");
    	user.setGender("male");
    	user.setBday("1991-07-08");
    	user.setAge(calculateAge(user.getBday()));
    	user.setRole("Senior Java Programmer");
    	controller.createUser(user);
    	
        Optional<User> optionalUser = controller.userRepository.findByName("Thirdy Ravena");
        
    	long getId = optionalUser.get().getId();
    	
    	User getUser = controller.userRepository.findById(getId).get();
    	
    	ResponseEntity<User> delUser = controller.deleteUser(getUser.getId());;

        if(!delUser.hasBody()){
        	delUser = null;
        }
    	
    	Assertions.assertNull(delUser);
    }
    
	private static int calculateAge(String bday) {	
	   LocalDate myObj = LocalDate.now();
	   LocalDate dob = LocalDate.parse(bday);
		
	   return Period.between(dob, myObj).getYears();
	}
}
