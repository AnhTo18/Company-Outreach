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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Owner Controller for Companies Logic Actions
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("prize")
class PrizeController {

    @Autowired
    PrizeRepository prizeRepository;
    
  

    private final Logger logger = LoggerFactory.getLogger(PrizeController.class);
    
    
 //TODO
    //consume method (redeem prize)
    
    
     /*end testing*/
    /**
	   * This method creates and adds a prize to the Prize Repository.
	   * THIS IS A POST METHOD, Path = /company/add
	   * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	   */
    @RequestMapping(value= "/add", method= RequestMethod.POST)
	public HashMap<String, String>  createCompany(@RequestBody Prize newprize) {
    	 HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Create new Prize method is invoked.");
		if(prizeRepository.findPrizeByprizeName(newprize.getPrizeName() ) == null) {
		 prizeRepository.save(newprize);
		 map.put("verify", "Added");
    }
		 return map;

	}

    
    /**
	   * This method finds all the Prize objects within the Prize Repository.
	   * THIS IS A GET METHOD, Path = /company
	   * FOR TESTING PURPOSES ONLY(?)
	   * @return List<Prize> This returns the list of prizes within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, path = "/getAll")
    public List<Prize> getAllCompanies() {
        logger.info("Entered into Controller Layer");
        List<Prize> results = prizeRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    /**
	   * This method finds the given Id Prize object within the Prize Repository.
	   * THIS IS A GET METHOD, Path = /prizes/{prizeName}
	   * @param String companyName
	   * @return Prizes This returns the single prize by id within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, path = "/{prizeName}")
    public Prize findOwnerById(@PathVariable("prizeName") String prizeName) {
        logger.info("Entered into Controller Layer");
      //  Optional<Owners> results = companyRepository.findById(id);j
        List<Prize> results = prizeRepository.findAll();
        prizeName = prizeName.toString().trim();
        for(Prize current : results) {
        	
        	if(current.getPrizeName().trim().equals(prizeName)) {
        		return current;
        	}
        	
        }
        return null; //NOT FOUND
    }
    
 
    /**
	   * This method deletes all the Prize objects within the Prize Repository.
	   * THIS IS A POST METHOD, Path = /prize/deleteall
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, path= "/prize/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all prizes is invoked.");
		prizeRepository.deleteAll();
	}
    
    
    /**
	   * This method deletes the ID Prize object within the Prize Repository.
	   * THIS IS A POST METHOD, Path = /company/delete/{id}
	   * @param int ID
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, value= "/prize/delete/{id}")
	public void deletePrizeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete Prize by id is invoked.");

		Optional<Prize> prize =  prizeRepository.findById(id);
		if(!prize.isPresent())
			throw new Exception("Could not find employee with id- " + id);

		prizeRepository.deleteById(id);
	}




}
