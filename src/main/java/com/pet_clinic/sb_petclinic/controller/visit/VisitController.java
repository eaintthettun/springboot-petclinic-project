package com.pet_clinic.sb_petclinic.controller.visit;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pet_clinic.sb_petclinic.model.Owner;
import com.pet_clinic.sb_petclinic.model.Pet;
import com.pet_clinic.sb_petclinic.model.Visit;
import com.pet_clinic.sb_petclinic.repository.OwnerRepository;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {
	private final OwnerRepository ownerRep;
	
	public VisitController(OwnerRepository ownerRep) {
		this.ownerRep=ownerRep;
	}
	
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(
			@PathVariable("ownerId")int ownerId,
			@PathVariable("petId")int petId,
			Map<String,Object> model) {
		Owner owner=this.ownerRep.findById(ownerId);
		Pet pet=owner.getPet(petId);
		
		model.put("pet", pet);
		model.put("owner", owner);
		
		Visit v=new Visit();
		
		return v;
	}
	
	@GetMapping("/visits/new")
	public String initNewVisitForm() {
		return "pets/createOrUpdateVisitForm";
	}
	
	@PostMapping("/visits/new")
	public String initNewVisitForm(@ModelAttribute Owner owner
			,@PathVariable("petId") int petId
			,Visit visit) {
		owner.addVisit(petId,visit);
		this.ownerRep.save(owner);
		return "redirect:/owners/{ownerId}";
	}
	
}
