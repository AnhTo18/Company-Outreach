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
package org.springframework.samples.outreach.company;

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
@RequestMapping("company")
class CompanyController {

    @Autowired
    CompanyRepository ownersRepository;
    
  

    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);
    
    
    /**
	   * This method creates and add a Company to the Owners Repository.
	   * THIS IS A POST METHOD, Path = /company/add
	   * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	   */
    @RequestMapping(value= "/add", method= RequestMethod.POST)
	public HashMap<String, String>  createEmployee(@RequestBody Companies newemp) {
    	 HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Create new User method is invoked.");
		 ownersRepository.save(newemp);
		 map.put("verify", "Added");

		 return map;

	}

    /**
	   * This method finds all the company objects within the company Repository.
	   * THIS IS A GET METHOD, Path = /company
	   * FOR TESTING PURPOSES ONLY(?)
	   * @return List<Owners> This returns the list of owners within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, path = "/owners")
    public List<Companies> getAllOwners() {
        logger.info("Entered into Controller Layer");
        List<Companies> results = ownersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    /**
	   * This method finds the given Id Owner object within the Owner Repository.
	   * THIS IS A GET METHOD, Path = /owners/{companyName}
	   * @param String companyName
	   * @return Owners This returns the single owner by id within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, path = "/{companyName}")
    public Companies findOwnerById(@PathVariable("companyName") String companyName) {
        logger.info("Entered into Controller Layer");
      //  Optional<Owners> results = ownersRepository.findById(id);j
        List<Companies> results = ownersRepository.findAll();
        companyName = companyName.toString().trim();
        for(Companies current : results) {
        	
        	if(current.getCompanyName().trim().equals(companyName)) {
        		return current;
        	}
        	
        }
        return null; //NOT FOUND
    }
    
    /**
	   * This method tries to Login with the given Username and Password. 
	   * It does this by searching through all Owner Objects within the 
	   * Owner Repository.
	   * THIS IS A GET METHOD, Path = /owners/login/{username}/{password}
	   * @param String Username
	   * @param String Password
	   * @return Map<String, String> This returns "verify", "true" || "verify", "false".
	   */
    @RequestMapping(value = "/owners/login/{username}/{password}", method = RequestMethod.GET)
    public Map<String, String> loginOwner( @PathVariable("username") String username, @PathVariable("password") String password) {
    	
     //   logger.info("Entered into Controller Layer");
//    	String username = "kordell";
//    	String password = "pass";
    	username = username.toString().trim();
    	password = password.toString().trim();
        List<Companies> results = ownersRepository.findAll();
        
        HashMap<String, String> map = new HashMap<>();
       
        
        for(Companies current : results) {
        	String currentUsername = current.getUsername().toString().trim();
        	String currentPassword = current.getpassword().toString().trim();
        	if(username.equals(currentUsername))
        	{
        		if(password.equals(currentPassword))
        		{
        		 map.put("verify", "true");
        		 return map;
        			
        		}
        	}
        }
         map.put("verify", "false");
         return map;
    }
    
    /**
	   * This method deletes all the Owner objects within the Owner Repository.
	   * THIS IS A POST METHOD, Path = /owners/deleteall
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, path= "/owners/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all employees is invoked.");
		ownersRepository.deleteAll();
	}
    
    /**
	   * This method deletes the ID Owner object within the Owner Repository.
	   * THIS IS A POST METHOD, Path = /owners/delete/{id}
	   * @param int ID
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, value= "/owners/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete employee by id is invoked.");

		Optional<Companies> emp =  ownersRepository.findById(id);
		if(!emp.isPresent())
			throw new Exception("Could not find employee with id- " + id);

		ownersRepository.deleteById(id);
	}




}
