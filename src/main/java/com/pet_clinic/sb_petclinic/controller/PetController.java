package com.pet_clinic.sb_petclinic.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pet_clinic.sb_petclinic.model.Owner;
import com.pet_clinic.sb_petclinic.model.Pet;
import com.pet_clinic.sb_petclinic.model.PetType;
import com.pet_clinic.sb_petclinic.model.PetValidator;
import com.pet_clinic.sb_petclinic.repository.OwnerRepository;
import com.pet_clinic.sb_petclinic.repository.PetTypeRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
	private final OwnerRepository ownerRep;
	private final PetTypeRepository petTypeRep;
	
	public PetController(OwnerRepository ownerRep,PetTypeRepository petTypeRep) {
		this.ownerRep=ownerRep;
		this.petTypeRep=petTypeRep;
	}
	
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petTypeRep.findPetTypes();
	}
	
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId")int ownerId) {
		//System.out.println("prepare owner model for every pet page");
		return this.ownerRep.findById(ownerId);
	}
	
	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("ownerId")int ownerId,@PathVariable(name="petId",required=false)Integer petId){
		//System.out.println("prepare pet model for every pet page");
		return petId==null?new Pet(): this.ownerRep.findById(ownerId).getPet(petId);
	}
	
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/*
	 * @InitBinder("pet") public void initPetBinder(WebDataBinder dataBinder) {
	 * System.out.println("pet binder preparation"); dataBinder.setValidator(new
	 * PetValidator()); System.out.println("pet validator prepared successfully"); }
	 */
	
	@GetMapping("/pets/new")
	public String initCreateForm(Owner owner,Model model) {
		//System.out.println("you click pet add button");
		Pet pet=new Pet();
		owner.addPet(pet); //important
		model.addAttribute("petObj", pet);
		return "pets/createOrUpdatePetForm";
	}
	
	@PostMapping("/pets/new")
	public String processCreateForm(Owner owner,Model model,@Valid Pet pet,BindingResult bresult) {
		if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true)!=null) {
			bresult.rejectValue("name", "duplicate", "already exists");
		}
		if(bresult.hasErrors()) {
			model.addAttribute("petObj", pet);
			return "pets/createOrUpdatePetForm";
		}
		 
		owner.addPet(pet); //important
		this.ownerRep.save(owner);
		return "redirect:/owners/{ownerId}";
	}
	
	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId,Model model,Owner owner) {
		Pet pet=owner.getPet(petId);
		model.addAttribute("petObj", pet);
		return "pets/createOrUpdatePetForm";
	}
	
	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(Owner owner,@Valid Pet pet,BindingResult bresult,Model model) {
		if(bresult.hasErrors()) {
			model.addAttribute("petObj", pet);
			return "pets/createOrUpdatePetForm";
		}
		owner.addPet(pet);
		this.ownerRep.save(owner);
		return "redirect:/owners/{ownerId}";
	}
	
	
	
	
	
	
	
	
}
