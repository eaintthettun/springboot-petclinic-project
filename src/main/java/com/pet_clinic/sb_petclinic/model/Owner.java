package com.pet_clinic.sb_petclinic.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="owners")
public class Owner extends PersonEntity{
	@Column(name="address")
	@NotEmpty
	private String address;
	
	@Column(name="city")
	@NotEmpty
	private String city;
	
	@Column(name="telephone")
	@NotEmpty
	@Digits(fraction=0,integer=10)
	private String telephone;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="owner_id")
	@OrderBy("name")
	private List<Pet> pets=new ArrayList<Pet>();
	
	
	public void addPet(Pet pet) {
		if(pet.isNew()) {
			getPets().add(pet);
		}
	}
	
	public Pet getPet(String name,boolean ignoreNew) {
		name=name.toLowerCase();
		for(Pet p:getPets()) {
			if(!ignoreNew || !p.isNew()) {
				String strName=p.getName();
				strName=(strName==null?"":strName.toLowerCase());
				if(strName.equals(name)) return p;
			}
		}
		return null;
	}
	
	public Pet getPet(Integer petId) {
		for(Pet p:this.pets) {
			if(!p.isNew()) {
				if(p.getId().equals(petId)) return p;
			}
		}
		return null;
	}

	public Owner addVisit(Integer pid,Visit v) {
		Pet p=getPet(pid);
		p.addVisit(v);
		return this;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}	
	
}
