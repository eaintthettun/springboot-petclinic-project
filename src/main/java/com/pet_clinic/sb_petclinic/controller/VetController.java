package com.pet_clinic.sb_petclinic.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pet_clinic.sb_petclinic.model.Owner;
import com.pet_clinic.sb_petclinic.model.Specialty;
import com.pet_clinic.sb_petclinic.model.Vet;
import com.pet_clinic.sb_petclinic.model.VetXml;
import com.pet_clinic.sb_petclinic.repository.SpecialtyRepository;
import com.pet_clinic.sb_petclinic.repository.VetRepository;

import jakarta.validation.Valid;

@Controller
public class VetController {
	private VetRepository vetRepo;
	private SpecialtyRepository specRepo;
	
	public VetController(VetRepository vetRepo,SpecialtyRepository specRepo) {
		this.vetRepo=vetRepo;
		this.specRepo=specRepo;
	}
	
	@GetMapping("/vets/find")
	public String initFindForm(Model model) {
		model.addAttribute("vet", new Vet());
		return "vets/findVet";
	}
	
	@GetMapping("/vets/new")
	public String initCreateForm(Model model) {
		model.addAttribute("vet", new Vet());
		model.addAttribute("specList",this.specRepo.findAll());
		return "vets/createOrUpdateVetForm";
	}
	
	@PostMapping("/vets/new")
	public String processCreateForm(@Valid Vet vet,BindingResult result) {
		if(result.hasErrors())
			return "vets/createOrUpdateVetForm";
		this.vetRepo.save(vet);
		return "redirect:/vets/"+vet.getId();
	}
	
	@GetMapping("/vets/{vetId}")
	public ModelAndView showVet(@PathVariable("vetId")int vetId) {
		Vet vet=this.vetRepo.findById(vetId);
		ModelAndView mav=new ModelAndView("vets/vetDetails");
		mav.addObject("vetDetails", vet);
		return mav;
	}
	
	@GetMapping("/vets/{vetId}/edit")
	public String initUpdateForm(@PathVariable("vetId")int vetId,Model model) {
		Vet vet=this.vetRepo.findById(vetId);
		model.addAttribute("vet", vet);
		model.addAttribute("specList",this.specRepo.findAll());
		return "vets/createOrUpdateVetForm";
	}
	
	@PostMapping("/vets/{vetId}/edit")
	public String processUpdateForm(@Valid @ModelAttribute("vet")Vet updatedVet, 
	                                BindingResult result,Model model,
	                                @PathVariable("vetId")int vetId) {
	    if (result.hasErrors()) {
	    	model.addAttribute("vet", updatedVet);
	        return "vets/createOrUpdateVetForm";
	    }
	    
	    Vet existingVet=this.vetRepo.findById(vetId);
	    existingVet.setFirstName(updatedVet.getFirstName());
	    existingVet.setLastName(updatedVet.getLastName());
	    existingVet.setSpecialties(updatedVet.getSpecialties());
	    
	    this.vetRepo.save(existingVet); // Save the vet object to the database

	    return "redirect:/vets/" + vetId;
	}

	/*
	 * @GetMapping("/vets") public ModelAndView processAllFind() { List<Vet>
	 * vetsList=this.vetRepo.findAll(); ModelAndView mav=new
	 * ModelAndView("vets/vetsList"); mav.addObject("vets", vetsList); return mav; }
	 */
	
	@GetMapping("/vets")
	public String processPageFind(@RequestParam(defaultValue="1")int page,
			Model model) {
		int pageSize=4;
		Pageable pageable=PageRequest.of(page-1, pageSize);
		Page<Vet> pageVet=this.vetRepo.findAllByPaged(pageable);
		
		VetXml vxml=new VetXml();
		vxml.getVetList().addAll(pageVet.toList());
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", pageVet.getTotalPages());
		//System.out.println("Current page="+page);
		model.addAttribute("totalItems", pageVet.getTotalElements());
		
		List<Vet> lv=pageVet.getContent();
		model.addAttribute("vets", lv);
		return "vets/vetsList";
	}
	
	@GetMapping("/vets/search")
	public ModelAndView processSearch(Vet vet) {
		List<Vet> vetsList=this.vetRepo.findByLastName(vet.getLastName());
		ModelAndView mav=new ModelAndView("vets/vetsList");
		mav.addObject("vets", vetsList);
		return mav;
	}
}













