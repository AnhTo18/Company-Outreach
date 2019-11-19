package org.springframework.samples.outreach.subscription;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Subscription Controller for Consumers and Companies Logic Actions
 * @author creimers
 * @author kschrock
 */
@RestController
public class SubscriptionController {
	@Autowired
    OwnerRepository ownersRepository;
    
    @Autowired
    CompanyRepository companyRepository;

	 /* subscription methods begin */
	    /**
		   * This method subscribes a user to a company.
		   * THIS IS A POST METHOD, Path = /owners/subscribe
		   * @return HashMap<String, String> This returns JSON data of "verify", "Subscribed".
		   */
	@RequestMapping(value= "/owners/subscribe/{ownerID}/{companyUsername}", method= RequestMethod.POST)
		public HashMap<String, String>  subscribeToCompany(@PathVariable("companyUsername") String cmpUserName,
				@PathVariable("ownerID") int ownerID) {
		 HashMap<String, String> map = new HashMap<>();
			System.out.println(this.getClass().getSimpleName() + " - Subscribe method is invoked.");
			/* add subscription logic here*/
			Company company = companyRepository.findCompanyByUsername(cmpUserName);
			for(Owner owner: company.getOwners()) {
				if(owner.getId() == ownerID) {
					 map.put("verify", "subscribed");
					 return map;
				}
				//its getting the owner by id and adding it to the list of owners in the company
			}
			Owner owner = ownersRepository.findById(ownerID).get();
			owner.getCompanies().add(company);
			company.getOwners().add(owner);
			ownersRepository.save(owner);
			/*end subscription logic */
			 map.put("verify", "Subscribed");
			 return map;
	
		}
    
    
}
