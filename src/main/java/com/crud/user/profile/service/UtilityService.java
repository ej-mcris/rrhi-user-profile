package com.crud.user.profile.service;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {
	public int calculateAge(String bday) {
		
		   LocalDate myObj = LocalDate.now();
		   LocalDate dob = LocalDate.parse(bday);
			
		   return Period.between(dob, myObj).getYears();
		}
}
