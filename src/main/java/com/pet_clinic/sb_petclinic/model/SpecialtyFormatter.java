package com.pet_clinic.sb_petclinic.model;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.pet_clinic.sb_petclinic.repository.SpecialtyRepository;
import com.pet_clinic.sb_petclinic.repository.VetRepository;

@Component
public class SpecialtyFormatter implements Formatter<Specialty>{
		private final SpecialtyRepository specRep;
	  
	  @Autowired
	  public SpecialtyFormatter(SpecialtyRepository specRep) {
		  System.out.println("Specialty type formatter");
		  this.specRep=specRep;
	  }
	  @Override
		public String print(Specialty object, Locale locale) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Specialty parse(String text, Locale locale) throws ParseException {
			System.out.println("parse method working for pet type formatter");
			Collection<Specialty> types=this.specRep.findAll();
			for(Specialty spt:types) {
				if(spt.getName().equals(text))
					return spt;
			}
			throw new ParseException("type not found"+text,0);
		}
	
}