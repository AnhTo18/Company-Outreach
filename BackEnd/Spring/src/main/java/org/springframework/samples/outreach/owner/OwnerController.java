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
package org.springframework.samples.outreach.owner;

import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.*;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.validation.BindingResult;
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

import javax.validation.Valid;

/**
 * Owner Controller for Consumers and Companies Logic Actions
 * @author creimers
 * @author kschrock
 */
@RestController
public class OwnerController {

    @Autowired
    OwnerRepository ownersRepository;
    
    @Autowired
    CompanyRepository companyRepository;
  

    private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    /*to save an users*/
//    @PostMapping( path = "/owners/new")
//    public String saveOwner(Owners owner) {
//        ownersRepository.save(owner);
//        return "New Owner "+ owner.getFirstName() + " Saved";
//    }
    
    
    /* subscription methods begin */
    /**
	   * This method subscribes a user to a company.
	   * THIS IS A POST METHOD, Path = /owners/subscribe
	   * @return HashMap<String, String> This returns JSON data of "verify", "Subscribed".
	   */
@RequestMapping(value= "/owners/subscribe/{username}/{companyUsername}", method= RequestMethod.POST)
	public HashMap<String, String>  subscribeToCompany(@PathVariable("companyUsername") String cmpUserName,
			@PathVariable("username") String username) {
	 HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Subscribe method is invoked.");
		/* add subscription logic here*/
		Owner currentOwner = null;
		for(Owner current: ownersRepository.findAll()) {
			if(current.getUsername().equals(username)) {
				currentOwner = current;
			}
		}
		Company company = companyRepository.findCompanyByUsername(cmpUserName);
		for(Subscription subscription: company.getSubscriptions()) {
			if(subscription.getOwner().getId() == currentOwner.getId()) {
				 map.put("verify", "subscribed");
				 return map;
			}
			//its getting the owner by id and adding it to the list of owners in the company
		}
		Owner owner = ownersRepository.findById(currentOwner.getId()).get();
		
		Subscription subscription = new Subscription();
		subscription.setCompany(company);
		subscription.setOwner(owner);
		owner.getSubscriptions().add(subscription);
	
		company.getSubscriptions().add(subscription);
		ownersRepository.save(owner);
		companyRepository.save(company);
		/*end subscription logic */
		 map.put("verify", "Subscribed");
		 return map;

	}

/**
 * This method returns all of the companies that the user has subscribed to. This searches through
 * the Repository to find the user and check companies they have subscribed to
 * THIS IS A POST METHOD, Path = /owners/subscriptions/{username}
 * @param int Points
 * @param String Username
 * @return HashMap<String, String> This returns JSON data of "verify", "Added" || "verify", "NotFound".
 */
@RequestMapping(value= "/owners/subscriptions/{username}", method= RequestMethod.GET)
public HashMap<String, String> checkSubscriptions(@PathVariable("username") String username ) {
/*basic concept:
 * return the companies subscribed to entry in the table
 */
	System.out.println(this.getClass().getSimpleName() + " - Check subscriptions method is invoked.");
	username = username.toString().trim();
	
//  List<Owner> results = ownersRepository.findAll();
  //Owner owners = new Owner();
  //owners = (Owner) results;
    HashMap<String, String> map = new HashMap<>();

// map.put(owners.getSubscriptions().toString(),"retrieved");
	 return map;

}

/*subscription methods end*/
    
    /**
	   * This method creates and add a User to the Owners Repository.
	   * THIS IS A POST METHOD, Path = /owners/add
	   * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	   */
    @RequestMapping(value= "/owners/add", method= RequestMethod.POST)
	public HashMap<String, String>  createEmployee(@RequestBody Owner newemp) {
    	 HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Create new User method is invoked.");
		 ownersRepository.save(newemp);
		 map.put("verify", "Added");
		 ownersRepository.flush();
		 return map;

	}
    
    /**
	   * This method adds the given points to the given user. This searches through
	   * the Repository to find the user and give them the given amount of points to them
	   * and updating the Repository. 
	   * THIS IS A POST METHOD, Path = /owners/addpoints/{points}/{username}
	   * @param int Points
	   * @param String Username
	   * @return HashMap<String, String> This returns JSON data of "verify", "Added" || "verify", "NotFound".
	   */
    @RequestMapping(value= "/owners/addpoints/{points}/{company}/{username}", method= RequestMethod.POST)
	public HashMap<String, String> addPoints(@PathVariable("points") int points, @PathVariable("company") String company , @PathVariable("username") String username ) {
	//This can be used once the user gets back the info from the other repo and confirms the points and sends it back to server.
    	username = username.toString().trim();
    	
        List<Owner> results = ownersRepository.findAll();
        
        HashMap<String, String> map = new HashMap<>();
       
        
        for(Owner current : results) {
        	String currentUsername = current.getUsername().toString().trim();
        	
        	if(username.toString().trim().equals(currentUsername))
        	{
        		
        		// map.put("verify", "Added");
        		
        		// System.out.println("This is the current points.");
        	//	 System.out.println(currentPoints);
        		
        		 for(Subscription subscription: current.getSubscriptions()) {
        				if(subscription.getCompany().getUsername().trim().equals(company.trim())) {
        					 int temp = 0;
        	        		 int currentPoints;
        	        		
        	        		 try {
        	        			 currentPoints =  subscription.getpoints();
        	        					 //Integer.parseInt(subscription.getCompany().getPoints());
        	        		 }
        	        		 catch (NumberFormatException e)
        	        		 {
        	        			 currentPoints = 0; //not found
        	        		 }
        	        		 temp = currentPoints + points; //add total points
        					System.out.println(currentPoints + "Before Amount");
        					System.out.println(temp + "After Amount");
        					subscription.setPoints(temp);
        					//subscription.setID(1);
        					 map.put("verify", "addedPoints!!");
        					 ownersRepository.flush(); // updates changes
        					 companyRepository.flush();
        					 return map;
        				}
        				//its getting the owner by id and adding it to the list of owners in the company
        			}
        	
        		// current.setPoints(String.valueOf(temp)); //set current points to current user
        		 ownersRepository.flush(); // updates changes
        		 
//        		 System.out.println("After current points.");
//        		 System.out.println(currentPoints);
        		 return map;
        	
        	}
        }
         
    
        map.put("verify", "NotFound");
		 return map;
	
	}

    /**
	   * This method finds all the Owner objects within the Owner Repository.
	   * THIS IS A GET METHOD, Path = /owners
	   * @return List<Owners> This returns the list of owners within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, path = "/owners")
    public List<Owner> getAllOwners() {
        logger.info("Entered into Controller Layer");
        List<Owner> results = ownersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/{password}/paid")
    public boolean userPaysApp(@PathVariable("username") String username, @PathVariable("password") String password) {
    	List<Owner> results = ownersRepository.findAll();
        username = username.toString().trim();
        password = password.toString().trim();
        for(Owner current : results) {
        	
        	if(current.getUsername().trim().equals(username)) {
        		
        		
        		if(current.getpassword().trim().equals(password)) {
        			current.setPaid("true");
        			ownersRepository.flush(); //update repo
        			
        			return true;
        		}
        	}
        	
        }
        return false;
        
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/{password}/{company}/paid")
    public boolean userPaysCompany(@PathVariable("username") String username, @PathVariable("password") String password,@PathVariable("company") String company) {
    	List<Owner> results = ownersRepository.findAll();
        username = username.toString().trim();
        password = password.toString().trim();
        for(Owner current : results) {
        	
        	if(current.getUsername().trim().equals(username)) {
        		
        		
        		if(current.getpassword().trim().equals(password)) {
        			
        			for(Subscription subscription: current.getSubscriptions()) {
        				if(subscription.getCompany().getUsername().trim().equals(company.trim())) {
        					subscription.getCompany().setPaidStatus(true);
                			ownersRepository.flush(); //update repo
                			companyRepository.flush();
        					 return true;
        				}
        			}
        			
        		}
        	}
        	
        }
        return false;
        
    }
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/findSubscriptions")
    public HashMap<String, String> findUserSubscriptions(@PathVariable("username") String username) {
    	List<Owner> results = ownersRepository.findAll();
    	username = username.toString().trim();
    	
    	 HashMap<String, String> map = new HashMap<>();
    	 
        for(Owner current : results) {
        	
        	
        	if(current.getUsername().trim().equals(username)) {
    			
    			for(Subscription subscription: current.getSubscriptions()) {
    				
            			map.put(subscription.getID()+"", subscription.getCompany().getCompanyName());
    				
    			}
    			
    		}
    	}
        	
        return map;
        
    }
    /**
	   * This method finds the given Id Owner object within the Owner Repository.
	   * THIS IS A GET METHOD, Path = /owners/{username}
	   * @param String Username
	   * @return Owners This returns the single owner by id within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, path = "/owners/{username}")
    public Owner findOwnerById(@PathVariable("username") String username) {
        logger.info("Entered into Controller Layer");
      //  Optional<Owners> results = ownersRepository.findById(id);j
        List<Owner> results = ownersRepository.findAll();
        username = username.toString().trim();
        for(Owner current : results) {
        	
        	if(current.getUsername().trim().equals(username)) {
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
        List<Owner> results = ownersRepository.findAll();
        
        HashMap<String, String> map = new HashMap<>();
       
        
        for(Owner current : results) {
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

		Owner emp =  (Owner) ownersRepository.findById(id).get();
		ownersRepository.deleteById(id);
	}




}
