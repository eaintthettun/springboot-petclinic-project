package com.pet_clinic.sb_petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pet_clinic.sb_petclinic.model.Pet;
import com.pet_clinic.sb_petclinic.model.PetType;

public interface PetTypeRepository extends Repository<PetType,Integer> {
	@Query("select ptype from PetType ptype order by ptype.name")
	@Transactional(readOnly=true)
	public List<PetType> findPetTypes();
}
