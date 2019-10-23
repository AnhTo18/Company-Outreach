package org.springframework.samples.outreach.qr;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.samples.outreach.qr.ZXingHelper;
import org.springframework.samples.outreach.owner.Owners;
import org.springframework.samples.outreach.qr.ProductService;

@Controller
@RequestMapping("product")
public class QRController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	ProductRepository productRepo;
	//Product p1;
	  private final Logger logger = LoggerFactory.getLogger(QRController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("products", productService.findAll());
		return "product/index";
	}

	@RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
	public void qrcode(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(ZXingHelper.getQRCodeImage(id, 200, 200));
		outputStream.flush();
		outputStream.close();
	}

	
	 //set quantity
	 @RequestMapping(value = "/{username}/{ password}/{ quant}", method = RequestMethod.GET)
	    public Map<String, String> quant( @PathVariable("username") String company, @PathVariable(" password") String id, @PathVariable(" quant") String quant) {
	    	
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
		 
		 int currentquantity = 0;
		 try {
			 currentquantity = Integer.parseInt(quant);
		 }
		 catch (NumberFormatException e)
		 {
			 currentquantity = 0; //not found
		 }
		
        Iterable<Product> yeet = productService.findAll(); //gets all the product types of qrcodes
       
		 for (Product current : yeet)
		 {
			 String currentCompany = current.getcompany().toString().trim();
			 if(company.toString().trim().equals(currentCompany)) {
				 
				if(current.getId() == currentId1) {
					 
					 map.put("verify", "Added");

	        		 current.setQuantity(currentquantity); //set current points to current user
	        		
	        		 productRepo.flush(); //update DataBase
	        		 return map;
	        		 
				}

			 }
	           
	    } 

        map.put("verify", "false");
        return map;
    }
	 @RequestMapping(value= "{id}/{company}", method= RequestMethod.POST)
		public HashMap<String, String> findCode(@PathVariable("id") int id, @PathVariable("company") String company ) {
		//This can be used once the user scans the code and gets the id and company. This will return json data of points to add to user
		 //once the user gets back the info they can confirm then sends another post to the owner repo to update their points.
		 company = company.toString().trim();
	    	
	    	Iterable<Product> results = productService.findAll();
	        
	        HashMap<String, String> map = new HashMap<>();
	       
	        
	        for(Product current : results) {
	        	String currentCompany = current.getcompany().toString().trim();
	        	
	        	if(company.toString().trim().equals(currentCompany))
	        	{
	        		
	        		
	        		if(current.getId() == id) {
	        			
	        			int change = current.getQuantity()-1;
		        		current.setcompany(change +"");
		        		
		        		productRepo.flush(); //update repo
		        		
		        		if(current.getQuantity() <= 0) {
		        			 map.put("points", "Error, Quantity is 0");
		        			 //when there in not points left, Error message.
		        			 return map;
		        		}
		        		
	        		 int currentPoints;
	        		 try {
	        			 currentPoints = (int) current.getpoints();
	        		 }
	        		 catch (NumberFormatException e)
	        		 {
	        			 currentPoints = 0; //not found
	        		 }
	        		 
	        		 map.put("points", currentPoints + "");
	        		 return map;
	        		}
	        	
	        	}
	        }
	         
	    
	        map.put("points", "NotFound");
			 return map;
		
		}
	
	 //consume?
	 @RequestMapping(value= "/{company}/{id}", method= RequestMethod.POST)
		public HashMap<String, String> findCode(@PathVariable("id") String id, @PathVariable("company") String company ) {
		//This can be used once the user scans the code and gets the id and company. This will return json data of points to add to user
		 //once the user gets back the info they can confirm then sends another post to the owner repo to update their points.
		
	    	
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
				 String currentCompany = current.getcompany().toString().trim();
				 
				 if(company.toString().trim().equals(currentCompany)) {
					 
					if(current.getId() == currentId1) {
						 	 
						if(current.getQuantity() < 1) {
							
								map.put("points", "No More Scans Left");
						        return map;
						}
						int newquantity = current.getQuantity() -1 ;
						
						
						
		        		 current.setQuantity(newquantity); //set current points to current user
		        		 
		        	
		        		 String points = current.getpoints() +"";
		        		 
		        		
		        		 map.put("points", points);
		        		
		        		
		        		 productRepo.flush(); //update DataBase
		        		
		        		 return map;
		        		 
					}

				 }
		           
		    } 

	        map.put("points", "Not Found");
	        return map;
		
		}



}
