package com.pet_clinic.sb_petclinic.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="visits")
public class Visit extends BaseEntity{
	@Column(name="visit_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="pet_id")
	private Pet pet;
	
	public Visit() {
		this.date=LocalDate.now();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	
}
