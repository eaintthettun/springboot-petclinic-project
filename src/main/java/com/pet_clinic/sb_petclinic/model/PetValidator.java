package com.pet_clinic.sb_petclinic.model;

import java.util.Set;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PetValidator implements Validator {
	private static final String REQUIRED="required";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Pet.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Pet p=(Pet) target;
		if(!StringUtils.hasLength(p.getName()))
			errors.rejectValue("name", REQUIRED, REQUIRED);
		if(p.getBirthDate()==null)
			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
		if(p.isNew() && p.getType()==null)
			errors.rejectValue("type", REQUIRED, REQUIRED);
	}
	
}
