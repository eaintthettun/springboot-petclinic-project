package com.pet_clinic.sb_petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pet_clinic.sb_petclinic.model.PetType;
import com.pet_clinic.sb_petclinic.model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty,Integer>{
	@Query("select refSpecialty from Specialty refSpecialty order by refSpecialty.name")
	@Transactional(readOnly=true)
	public List<Specialty> findAll();
	
	@Query("select refSpecialty from Specialty refSpecialty where refSpecialty.id=:id")
	@Transactional(readOnly=true)
	public Specialty findById(@Param("id")int id);

	List<Specialty> findAllById(Iterable<Integer> ids);
	
}
