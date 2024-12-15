package com.pet_clinic.sb_petclinic.model;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.pet_clinic.sb_petclinic.repository.OwnerRepository;
import com.pet_clinic.sb_petclinic.repository.PetTypeRepository;

@Component
public class PetTypeFormatter implements Formatter<PetType>{
	private final PetTypeRepository petTypeRep;
	
	@Autowired
	public PetTypeFormatter(OwnerRepository ownerRep,PetTypeRepository petTypeRep) {
		this.petTypeRep=petTypeRep;
	}
	@Override
	public String print(PetType object, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		Collection<PetType> types=this.petTypeRep.findPetTypes();
		for(PetType pt:types) {
			if(pt.getName().equals(text))
				return pt;
		}
		throw new ParseException("type not found"+text,0);
	}
	
}