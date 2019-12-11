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

public class SubscriptionService {
	
	
	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;
	
	
	/* subscription methods begin */
	/**
	 * This method subscribes a user to a company. THIS IS A POST METHOD, Path =
	 * /owners/subscribe
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify",
	 *         "Subscribed".
	 */
	public HashMap<String, String> subscribeToCompany(Owner ownerpass, Company companypass) {
		HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Subscribe method is invoked.");

		Owner currentOwner = ownersRepository.findOwnerByUsername(ownerpass.getUsername());
		Company company = companyRepository.findCompanyByUsername(companypass.getCompanyName());
		for (Subscription subscription : company.getSubscriptions()) {
			if (subscription.getOwner().getId() == currentOwner.getId()) {
				map.put("verify", "Already Subscribed");
				return map;
			}
			// its getting the owner by id and adding it to the list of owners in the
			// company
		}
		Owner owner = ownersRepository.findById(currentOwner.getId()).get();

		Subscription subscription = new Subscription();
		subscription.setCompany(company);
		subscription.setOwner(owner);
		owner.getSubscriptions().add(subscription);

		company.getSubscriptions().add(subscription);
		ownersRepository.save(owner);
		companyRepository.save(company);
		/* end subscription logic */
		map.put("verify", "Subscribed");
		return map;

	}
}
