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
import org.springframework.samples.outreach.generate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.generate.QRCodeGenerator;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.security.acl.Owner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

/**
 *
 */
@RestController
class OwnerController {

    @Autowired
    OwnerRepository ownersRepository;
   
    
  

    private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    /*to save an users*/
//    @PostMapping( path = "/owners/new")
//    public String saveOwner(Owners owner) {
//        ownersRepository.save(owner);
//        return "New Owner "+ owner.getFirstName() + " Saved";
//    }
    @RequestMapping(value= "/owners/add", method= RequestMethod.POST)
	public String createEmployee(@RequestBody Owners newemp) {
		System.out.println(this.getClass().getSimpleName() + " - Create new employee method is invoked.");
		 ownersRepository.save(newemp);
		 return "New Owner " + newemp.getFirstName() + " Saved";
	}

    @RequestMapping(value= "/owners/addpoints/{points}/{username}", method= RequestMethod.POST)
	public HashMap<String, String> addPoints(@PathVariable("points") int points, @PathVariable("username") String username ) {
	
    	username = username.toString().trim();
    	
        List<Owners> results = ownersRepository.findAll();
        
        HashMap<String, String> map = new HashMap<>();
       
        
        for(Owners current : results) {
        	String currentUsername = current.getUsername().toString().trim();
        	
        	if(username.equals(currentUsername))
        	{
        		
        		 map.put("verify", "Added");
        		 int temp = 0;
        		 int currentPoints;
        		 try {
        			 currentPoints = Integer.parseInt(current.getPoints());
        		 }
        		 catch (NumberFormatException e)
        		 {
        			 currentPoints = 0; //not found
        		 }
        		 temp = currentPoints + points; //add total points
        	
        		 current.setPoints(String.valueOf(temp)); //set current points to current user
        		 return map;
        	
        	}
        }
         
    
        map.put("verify", "NotFound");
		 return map;
	
	}

    /*get all users*/
    @RequestMapping(method = RequestMethod.GET, path = "/owners")
    public List<Owners> getAllOwners() {
        logger.info("Entered into Controller Layer");
        List<Owners> results = ownersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    /*get user by user id*/
    @RequestMapping(method = RequestMethod.GET, path = "/owners/{ownerId}")
    public Optional<Owners> findOwnerById(@PathVariable("ownerId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Owners> results = ownersRepository.findById(id);
        return results;
    }
    
    @RequestMapping(value = "/owners/login/{username}/{password}", method = RequestMethod.GET)
    public Map<String, String> loginOwner( @PathVariable("username") String username, @PathVariable("password") String password) {
    	
     //   logger.info("Entered into Controller Layer");
//    	String username = "kordell";
//    	String password = "pass";
    	username = username.toString().trim();
    	password = password.toString().trim();
        List<Owners> results = ownersRepository.findAll();
        
        HashMap<String, String> map = new HashMap<>();
       
        
        for(Owners current : results) {
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
    //Delete all users
    @RequestMapping( method= RequestMethod.POST, path= "/owners/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all employees is invoked.");
		ownersRepository.deleteAll();
	}
   //test QRgen 
    @RequestMapping(method = RequestMethod.GET, path="/owners/generate")
	public void generate(ModelMap modelMap) {
		//String[] codefill = {"test","one","two","three"};
    	//String code = "25615465464";
		//System.out.print(QRCodeGenerator.main(code));

		try {
			String s = new String (QRCodeGenerator.getQRCodeImage("yo", 200, 200));
			System.out.print(s);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    //Delete User by ID
    @RequestMapping( method= RequestMethod.POST, value= "/owners/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete employee by id is invoked.");

		Optional<Owners> emp =  ownersRepository.findById(id);
		if(!emp.isPresent())
			throw new Exception("Could not find employee with id- " + id);

		ownersRepository.deleteById(id);
	}


}
