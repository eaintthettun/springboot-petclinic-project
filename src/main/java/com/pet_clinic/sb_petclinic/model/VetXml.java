package com.pet_clinic.sb_petclinic.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/*<vetList>
<vet>
	<id></id>
	<firstName></firstName>
	<lastName></lastName>
	<specialtiesXml>
		<specialty>
			<name></name> //sorted
		</specialty>
	</specialtiesXml>
</vet>
</vetList>*/

@XmlRootElement
public class VetXml {
	private List<Vet> vets;
	
	@XmlElement
	public List<Vet> getVetList(){
		if(this.vets==null) this.vets=new ArrayList<Vet>();
		return this.vets;
	}
}
