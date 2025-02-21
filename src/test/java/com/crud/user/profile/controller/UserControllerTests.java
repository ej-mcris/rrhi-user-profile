package com.crud.user.profile.controller;


import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.crud.user.profile.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserControllerTests {
    @Autowired
    private UserController controller;
    
    static User user = new User();
    
    @BeforeAll
    static void setup() {
    	try {
    		user.setId(1L);
			user.setName("Juan Carlo Miguel");
	    	user.setEmail("jcmiguel@gmail.com");
	    	user.setGender("male");
	    	user.setBday("1991-07-08");
	    	user.setAge(calculateAge(user.getBday()));
			user.setRole("Java Programmer");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @DisplayName("Create User Profile")
    @Test
    void createUser() throws Exception {
		Assertions.assertSame(user.getId(), controller.createUser(user).getId());
    }
    
    @DisplayName("Read user profile")
    @Test
    void readUser() throws Exception {
    	ResponseEntity<User> getUser = controller.getUser(1L);
    	
    	Assertions.assertSame(getUser.getBody().getId(), user.getId());
    }
    
    @DisplayName("Update user profile")
    @Test
    void updateuser() throws Exception {
    	String expectedRole = "Java Programmer";
    	
		user.setRole("Senior Java Programmer");
		String checkRole = controller.updateUser(1L, user).getBody().getRole();
		
		Assertions.assertNotEquals(expectedRole, checkRole);
    }
    
    @DisplayName("Delete user profile")
    @Test
    void deleteuser() throws Exception {
    	controller.deleteUser(1L);
    	
    	Assertions.assertEquals("User not found with id: 1", controller.getUser(1L).getBody());
    }
    
	private static int calculateAge(String bday) {	
	   LocalDate myObj = LocalDate.now();
	   LocalDate dob = LocalDate.parse(bday);
		
	   return Period.between(dob, myObj).getYears();
	}
}
