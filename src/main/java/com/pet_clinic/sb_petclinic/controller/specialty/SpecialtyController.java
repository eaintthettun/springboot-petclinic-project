package com.pet_clinic.sb_petclinic.controller.specialty;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet_clinic.sb_petclinic.model.Owner;
import com.pet_clinic.sb_petclinic.model.Specialty;
import com.pet_clinic.sb_petclinic.model.Vet;
import com.pet_clinic.sb_petclinic.repository.SpecialtyRepository;
import com.pet_clinic.sb_petclinic.repository.VetRepository;

@Controller
@RequestMapping("/vets/{vetId}")
public class SpecialtyController {
	private VetRepository vetRepo;
	private SpecialtyRepository specRepo;
	
	public SpecialtyController(VetRepository vetRepo,SpecialtyRepository specRepo) {
		this.vetRepo=vetRepo;
		this.specRepo=specRepo;
	}
	
	@ModelAttribute("vet")
	public Vet findOwner(@PathVariable("vetId")int vetId) {
		//System.out.println("prepare vet model for every vet page");
		return this.vetRepo.findById(vetId);
	}
	
	@GetMapping("/specialties/new")
	public String initSpecialtyForm(Vet vet,Model model) {
		Specialty specialty=new Specialty();
		model.addAttribute("specialty", specialty);
		List<Specialty> specList=this.specRepo.findAll();
		specList.removeAll(vet.getSpecialties());
		model.addAttribute("specList", specList);
		return "specialty/addNewSpecialtyToVetForm";
	}
	
	@PostMapping("/specialties/new")
	public String processSpecialtyForm(Vet vet,@RequestParam("specialtyId")int specialtyId) {
		Specialty specialty=this.specRepo.findById(specialtyId);
		vet.getSpecialties().add(specialty);
		this.vetRepo.save(vet);
		return "redirect:/vets/{vetId}";
	}
}
