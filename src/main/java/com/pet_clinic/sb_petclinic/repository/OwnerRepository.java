package com.pet_clinic.sb_petclinic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pet_clinic.sb_petclinic.model.Owner;

public interface OwnerRepository extends Repository<Owner,Integer> {
	//all owner (not just one owner)
	@Query("select refOwner from Owner refOwner")
	@Transactional(readOnly=true)
	public List<Owner> findAll();
	
	@Query("select refOwner from Owner refOwner")
	@Transactional(readOnly=true)
	public Page<Owner> findAllByPaged(Pageable pageable);

	
	public void save(Owner owner);//save==insert
	
	//findById<-- developer method Name(customized)
	@Query("select refOwner from Owner refOwner where refOwner.id=:id")
	@Transactional(readOnly=true)
	public Owner findById(@Param("id")Integer id);
	
	@Query("select refOwner "+
			"from Owner refOwner "+
			"where refOwner.lastName like :lastName%")
	@Transactional(readOnly=true)
	public List<Owner> findByLastName(@Param("lastName")String lastName);

	
}
