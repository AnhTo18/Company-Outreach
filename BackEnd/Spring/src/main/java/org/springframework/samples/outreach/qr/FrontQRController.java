package org.springframework.samples.outreach.qr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller for the qrcodes, backend version.
 * @author creimers
 * @author kschrock
 */
@RestController
public class FrontQRController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OwnerRepository ownersRepository;
	    
	@Autowired
	CompanyRepository companyRepository;
	//Product p1;
	  private final Logger logger = LoggerFactory.getLogger(QRController.class);
	
	 
	  /**
	   * This method gets all the Codes in the Product Repository.
	   * THIS IS A GET METHOD, Path = /Work 
	   * @return Iterable<Product> This returns the list of QR code Objects.
	   */
	    @RequestMapping(method = RequestMethod.GET, path = "/Work")
	    public Iterable<Product> getAllCodes() {
	        logger.info("Entered into Controller Layer");
	        Iterable<Product> results = productService.findAll();
	        //logger.info("Number of Records Fetched:" + results.size());
	        
	        return results;
	    }

	    /**
		   * This method sets the given quantity to the Correct QR Code and Update it
		   * within the Product Repository.
		   * THIS IS A POST METHOD, Path = /{company}/{ id}/{ quant}
		   * @param Company
		   * @param ID
		   * @param Quanity
		   * @return Map<String, String> This returns "Quantity", "Added" or "Quantity", "Did Not Add".
		   */
		 @RequestMapping(value = "/{company}/{ id}/{ quant}", method = RequestMethod.POST)
		    public Map<String, String> setQuantity( @PathVariable("company") String company, @PathVariable(" id") String id, @PathVariable(" quant") String quant) {
		    //This method will set the given Quantity to the correct QR Code and update it.
			 company = company.toString().trim();
			 //make sure to trim extra space
	        HashMap<String, String> map = new HashMap<>();
	        //this HashMap will return the JSON DATA
	        
	        int currentId1 = 0;
	        
			 try {
				 currentId1 = Integer.parseInt(id);
				//Correctly parsed the given Id 
			 }
			 catch (NumberFormatException e)
			 {
				 currentId1 = 0; 
				 //Wrong Format 
			 }
			 
			 int currentquantity = 0;
			 try {
				 currentquantity = Integer.parseInt(quant);
				 //Correctly parsed the given Quantity
			 }
			 catch (NumberFormatException e)
			 {
				 currentquantity = 0;
				//Wrong Format 
			 }
			
	        Iterable<Product> totalObjects = productService.findAll(); //gets all the product types of qrcodes
	       
			 for (Product current : totalObjects)
			 {
				 String currentCompany = current.getcompany().toString().trim();
				 //create Current Company by getting the current Product Object 
				 
				 if(company.toString().trim().equals(currentCompany)) {
					 //compare the Given Company name to the Current Product Company Name
					 
					if(current.getId() == currentId1) {
						//compare the Given Id to the Current Product Id
						 
						 map.put("Quantity", "Added");
						 //RETURN JSON DATA

		        		 current.setQuantity(currentquantity);
		        		 //set current points to current user
		        		
		        		 productRepo.flush(); 
		        		 //update DataBase
		        		 return map; 
					}

				 }
		           
		    } 

	        map.put("Quantity", "Did Not Add");
	        //RETURN JSON DATA
	        return map;
	    }
		 
		 /**
		   * This can be used once the user scans the code and gets the id and company. This will return json data of points to add to user
		   * once the user gets back the info they can confirm then sends another post to the owner repo to update their points.
		   * THIS IS A GET METHOD, Path = /{company}/{id}
		   * @param Company
		   * @param ID
		   * @return HashMap<String, String>  This returns "points", "No More Scans Left" || "points", "Not Found" || ex. "points", "123".
		   */
		 @RequestMapping(value= "Qr/{company}/{id}/{username}", method= RequestMethod.POST)
			public HashMap<String, String> findCode(@PathVariable("id") String id, @PathVariable("company") String company, @PathVariable("username") String username ) {
			//This can be used once the user scans the code and gets the id and company. This will return json data of points to add to user
			 //once the user gets back the info they can confirm then sends another post to the owner repo to update their points.
			 username = username.toString().trim();
 	    	
 	       	List<Owner> results = ownersRepository.findAll();
 	        
 	       	HashMap<String, String> map2 = new HashMap<>();
		    	
 	       	int currentPoints = 0;
			 company = company.toString().trim();
		        HashMap<String, String> map = new HashMap<>();
		        int currentId1 = 0;
				 try {
					 currentId1 = Integer.parseInt(id);
				 }
				 catch (NumberFormatException e)
				 {
					 currentId1 = 0; //not found
				 }
				 
				//System.out.println(currentId1);
		        Iterable<Product> yeet = productService.findAll(); //gets all the product types of qrcodes
		       
				 for (Product current : yeet)
				 {
//					 String currentCompany = current.getcompany().toString().trim();
//					 
//					 if(company.toString().trim().equals(currentCompany)) {
						 
						if(current.getId() == currentId1) {
							 	 
							if(current.getQuantity() < 1) {
								
									map.put("points", "No More Scans Left");
							        return map;
							}
							int newquantity = current.getQuantity() -1 ;
							
							
							
			        		 current.setQuantity(newquantity); //set current points to current user
			        		 
			        	
			        		 String points = current.getpoints() +"";
			        		 int points2 = (int)(current.getpoints());
			        		
			        		 map.put("points", points);
			        		
			        		
			        		 productRepo.flush(); //update DataBase
			        
			        	       
			        	        
			        	        for(Owner currentUser : results) {
			        	        	String currentUsername = currentUser.getUsername().toString().trim();
			        	        	
			        	        	if(username.toString().trim().equals(currentUsername))
			        	        	{
			        	        		
			        	        		// map.put("verify", "Added");
			        	        		
			        	        		// System.out.println("This is the current points.");
			        	        	//	 System.out.println(currentPoints);
			        	        		
			        	        		 for(Subscription subscription: currentUser.getSubscriptions()) {
			        	        				if(subscription.getCompany().getCompanyName().trim().equals(company.trim())) {
			        	        					 int temp = 0;
			        	        	        		
			        	        	        		 try {
			        	        	        			 currentPoints =  (int) subscription.getpoints();
			        	        	        					 //Integer.parseInt(subscription.getCompany().getPoints());
			        	        	        		 }
			        	        	        		 catch (NumberFormatException e)
			        	        	        		 {
			        	        	        			 currentPoints = 0; //not found
			        	        	        		 }
			        	        	        		 temp = currentPoints + points2; //add total points
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
			        	        		 
//			        	        		 System.out.println("After current points.");
//			        	        		 System.out.println(currentPoints);
			        	        		 return map;
			        	        	
			        	        	}
			 
						}

					 }
			          
			
			}
				map.put("points", "Not Found and Not Added");
				return map;
		 }
		
}

