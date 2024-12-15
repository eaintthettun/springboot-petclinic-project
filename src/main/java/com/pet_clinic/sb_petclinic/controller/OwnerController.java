package com.pet_clinic.sb_petclinic.controller;

import java.util.List;
import java.util.Map;

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
import com.pet_clinic.sb_petclinic.repository.OwnerRepository;

import jakarta.validation.Valid;

@Controller
public class OwnerController {
	private final OwnerRepository ownerRep;
	
	public OwnerController(OwnerRepository ownerRep) {
		this.ownerRep=ownerRep;
	}
	
	/*@GetMapping("/owners")
	public String processFind(Model model) {
		List<Owner> ownersList=this.ownerRep.findAll();
		model.addAttribute("listOwners", ownersList);
		return "owners/ownersList";
	}*/
	
	@GetMapping("/owners")
	public String processPageFind(@RequestParam(defaultValue="1")int page,Model model) {
		int recordPerPage=5;
		Pageable pageable=PageRequest.of(page-1, recordPerPage);
		Page<Owner> pageOwner=this.ownerRep.findAllByPaged(pageable);
		//System.out.println("Total pages="+pageOwner.getTotalPages());
		//System.out.println("Total items="+pageOwner.getTotalElements());
		//retrieve not all items in the list. only retrieve records per page
		//System.out.println("Total records per page="+pageOwner.getContent().size());
		
		model.addAttribute("currentPage", page);
		System.out.println("Your current page="+page);
		model.addAttribute("totalPages", pageOwner.getTotalPages());
		model.addAttribute("totalItems", pageOwner.getTotalElements());
		List<Owner> listOwners=pageOwner.getContent();
		model.addAttribute("listOwners", listOwners);
		
		
		return "owners/ownersList";
	}
	
	@GetMapping("/owners/find")
	public String initFindForm(Map<String,Object> model) {
		model.put("owner", new Owner());
		return "owners/findOwner";
	}
	
	@GetMapping("/owners/new")
	public String initCreateForm(Map<String,Object> model) {
		//System.out.println("you click owner add button");
		model.put("owner", new Owner());
		return "owners/createOrUpdateOwnerForm";
	}
	
	@PostMapping("/owners/new")
	public String processCreateForm(@Valid Owner owner,BindingResult result) {
		if(result.hasErrors())
			return "owners/createOrUpdateOwnerForm";
		this.ownerRep.save(owner);
		return "redirect:/owners/"+owner.getId();
	}
	
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId")int ownerId) {
		//System.out.println("You have new owner id="+ownerId);
		Owner myOwner=this.ownerRep.findById(ownerId);
		ModelAndView mav=new ModelAndView("owners/ownerDetails");
		mav.addObject("ownerDetails",myOwner);
		return mav;
	}
	
	 @GetMapping("/owners/{ownerId}/edit") 
	 public String initUpdateOwnerForm(@PathVariable("ownerId")int ownerId,Model model) {
		  //System.out.println("You clicked edit button");
		 Owner owner=this.ownerRep.findById(ownerId);
		 model.addAttribute("owner",owner); 
		 return "owners/createOrUpdateOwnerForm"; 
	  }
	
	 @PostMapping("/owners/{ownerId}/edit") 
	 public String processUpdateOwnerForm(@Valid @ModelAttribute("owner")Owner updatedOwner
			 ,BindingResult bresult
			 ,@PathVariable("ownerId")int ownerId,Model model) {
		  if(bresult.hasErrors()) {
			  model.addAttribute("owner", updatedOwner);
			  return "owners/createOrUpdateOwnerForm";
		  }
		  Owner existingOwner=this.ownerRep.findById(ownerId);
		  existingOwner.setFirstName(updatedOwner.getFirstName());
		  existingOwner.setLastName(updatedOwner.getLastName());
		  existingOwner.setAddress(updatedOwner.getAddress());
		  existingOwner.setCity(updatedOwner.getCity());
		  existingOwner.setTelephone(updatedOwner.getTelephone());
		  
		  this.ownerRep.save(existingOwner);
		  
		  return "redirect:/owners/"+ownerId; 
	  }
	 
	 @GetMapping("/owners/search")
	 public ModelAndView initSearch(Owner owner) {
		 //System.out.println("init search");
		 List<Owner> ownersList=this.ownerRep.findByLastName(owner.getLastName());
		 ModelAndView mav=new ModelAndView("/owners/ownersList");
		 mav.addObject("listOwners",ownersList);
		 return mav;
	 }
}
