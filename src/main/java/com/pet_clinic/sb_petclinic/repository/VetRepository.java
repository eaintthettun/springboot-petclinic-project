package com.pet_clinic.sb_petclinic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pet_clinic.sb_petclinic.model.Owner;
import com.pet_clinic.sb_petclinic.model.Vet;

public interface VetRepository extends Repository<Vet,Integer> {
	public void save(Vet vet);
	
	@Query("select refVet from Vet refVet where refVet.id=:id")
	@Transactional(readOnly=true)
	public Vet findById(@Param("id")int id); 

	@Query("select refVet from Vet refVet")
	@Transactional(readOnly=true)
	public List<Vet> findAll();
	
	@Query("select refVet from Vet refVet where refVet.lastName=:lastName")
	@Transactional(readOnly=true)
	public List<Vet> findByLastName(@Param("lastName")String lastName);

	@Query("select refVet from Vet refVet")
	@Transactional(readOnly=true)
	public Page<Vet> findAllByPaged(Pageable pageable);
	
}
