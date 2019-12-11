/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.outreach.prize;

import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Owner Controller for Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("prize")
class PrizeController {

	@Autowired
	PrizeRepository prizeRepository;

	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;

	private final Logger logger = LoggerFactory.getLogger(PrizeController.class);

	/**
	 * This method creates and adds a prize to the Prize Repository. THIS IS A POST
	 * METHOD, Path = /prize/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/addPrize", method = RequestMethod.POST)
	public HashMap<String, String> createPrize(@RequestBody Prize newprize) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		System.out.println(this.getClass().getSimpleName() + " - Create new Prize method is invoked.");
		// print to the console
		prizeRepository.save(newprize);
		// save the prize to the repository
		prizeRepository.flush();
		// update repository
		map.put("verify", "Added");
		// add return value
		return map;
		// return map

	}

	/**
	 * edits prize information
	 * 
	 * @param id
	 * @param modPrize
	 * @return
	 */
	// edit prize info
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public HashMap<String, String> editPrize(@PathVariable int id, @RequestBody Prize modPrize) {
		System.out.println(this.getClass().getSimpleName() + " - Edit Prize method is invoked.");
		HashMap<String, String> map = new HashMap<>();
		// changes cost
		int costChange = modPrize.getCost(); // gets proper info
		prizeRepository.findPrizeByPrizename(modPrize.getPrizename()).setCost(costChange);
		// changes qty
		int qtyChange = modPrize.getQty(); // gets proper info
		prizeRepository.findPrizeByPrizename(modPrize.getPrizename()).setQty(qtyChange);
		// changes prizename
		// just delete prize and re add it with new name instead

		prizeRepository.flush();
		map.put("verify", "edited");
		return map;
	}

	/**
	 * This method finds all the Prize objects within the Prize Repository. THIS IS
	 * A GET METHOD, Path = /prize FOR TESTING PURPOSES ONLY(?)
	 * 
	 * @return List<Prize> This returns the list of prizes within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getAll/Prizes")
	public List<Prize> getAllCompanies() {
		logger.info("Entered into Controller Layer");
		// print to the console
		List<Prize> results = prizeRepository.findAll();
		// get all the prizes in the repository
		logger.info("Number of Records Fetched:" + results.size());
		// print to the console
		return results;
		// return the list of prizes
	}

	/**
	 * This method finds the given Id Prize object within the Prize Repository. THIS
	 * IS A GET METHOD, Path = /prizes/{prizeName}
	 * 
	 * @param String companyName
	 * @return Prizes This returns the single prize by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{prizeName}")
	public Prize findOwnerById(@PathVariable("prizeName") String prizeName) {
		logger.info("Entered into Controller Layer");
		// print to the console
		List<Prize> results = prizeRepository.findAll();
		// this gets the results all the prizes from the repostory
		prizeName = prizeName.toString().trim();
		// given prize name to match
		for (Prize current : results) {
			// this iterates through all the prizes in the repository
			if (current.getPrizename().trim().equals(prizeName)) {
				// this checks if the current prize name matches the given prize name
				return current;
				// this returns the current prize
			}

		}
		return null;
		// NOT FOUND
	}

	/**
	 * This method deletes all the Prize objects within the Prize Repository. THIS
	 * IS A POST METHOD, Path = /prize/deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/prize/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all prizes is invoked.");
		//print to the console
		prizeRepository.deleteAll();
		//deletes all the prizes in the repository
	}

	
	/**
	 * This method deletes the ID Prize object within the Prize Repository. THIS IS
	 * A POST METHOD, Path = /company/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/prize/delete/{id}")
	public void deletePrizeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete Prize by id is invoked.");
		//print to the console

		Optional<Prize> prize = prizeRepository.findById(id);
		if (!prize.isPresent())
			// this checks if the current prize is not in repository
			throw new Exception("Could not find prize with id- " + id);

		prizeRepository.deleteById(id);
		// this deletes the prize from repostory by ID
	}

}
