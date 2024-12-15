package com.pet_clinic.sb_petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;

@Entity
@Table(name="vets")
public class Vet extends PersonEntity{
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="vet_specialties",
	joinColumns= @JoinColumn(name="vet_id"),
	inverseJoinColumns= @JoinColumn(name="specialty_id"))
	private Set<Specialty> specialties;

	
	public Set<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
		this.specialties = specialties;
	}
	
	@XmlElement
	public List<Specialty> getSpecialtiesXml(){
		List<Specialty> specList=new ArrayList(getSpecialties());
		PropertyComparator.sort(specList, new MutableSortDefinition("name",true,true));
		return Collections.unmodifiableList(specList);
	}
	
	public int getNoOfSpecialties() {
		return this.getSpecialties().size();
	}
}
