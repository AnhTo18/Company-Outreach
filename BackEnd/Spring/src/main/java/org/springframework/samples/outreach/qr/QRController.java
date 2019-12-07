package org.springframework.samples.outreach.qr;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.Current;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.samples.outreach.qr.ZXingHelper;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.qr.QRService;
/**
 * This is the QR Contoller that Generates the QR codes with the Correct info Company, Content, Points, Quantity left and ID.
 * @author creimers
 * @author kschrock
 */
@Controller
@RequestMapping("QRcode")
public class QRController {

	@Autowired
	private QRService QRservice;
	
	@Autowired
	QRRepository QRrepo;
	//Product p1;
	  private final Logger logger = LoggerFactory.getLogger(QRController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		modelMap.put("products", QRservice.findAll());
		return "product/index";
	}
//	 /**
//	   * THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE. 
//	   * This is only used for generation qr codes from the WorkBench.
//	   * THIS IS A GET METHOD, Path = qrcode/{id}
//	   * @param STRING ID
//	   * @return Void
//	   */
//	@RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
//	public void qrcode(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
//		//THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE
//		response.setContentType("image/png");
//		OutputStream outputStream = response.getOutputStream();
//		 Iterable<QRcode> all = QRservice.findAll();
//		 //This gets all the current Data in the Repository
//		 
//		 String baseURL = "http://localhost:8080";
//		// String baseURL = "http://coms-309-ss-8.misc.iastate.edu:8080";
//		 //This is our Base URL for the QR Codes
//		 
//		 for(QRcode current : all) {
//			 
//			String currentId = current.getId() + "";
//			//Get current Id for the Product Object
//			
//			if(currentId.equals(id)) {
//				baseURL = baseURL +"/" + current.getcompany();
//				//add to the base URL
//				baseURL = baseURL +"/" + current.getId();
//				//add to the base URL
//				outputStream.write(ZXingHelper.getQRCodeImage(baseURL, 200, 200));
//				//Write the content to the QR code
//				outputStream.flush();
//				//Update the Repository
//							}
//		 }
//		//outputStream.write(ZXingHelper.getQRCodeImage(baseURL, 200, 200));
//		outputStream.flush();
//		//Update the Repository
//		outputStream.close();
//		//Close the Steam
//	}

	
	 /**
	   * THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE. 
	   * This is only used for generation qr codes from the WorkBench.
	   * THIS IS A GET METHOD, Path = qrcode/{id}
	   * @param STRING ID
	   * @return Void
	   */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public HashMap<String, String> qrcode(@RequestBody QRcode codegen, 
			HttpServletResponse response) throws Exception {
		//THIS METHOD CREATES THE QR CODES AND WRTIES THEM INTO THE DATABASE
		HashMap<String, String> map = new HashMap<>();
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		 Iterable<QRcode> all = QRservice.findAll();
		 //This gets all the current Data in the Repository
		 QRrepo.save(codegen);
		 
		 String baseURL = "http://localhost:8080/QRcode";
		// String baseURL = "http://coms-309-ss-8.misc.iastate.edu:8080";
		 //This is our Base URL for the QR Codes
//		QRrepo.findAll().get(all.toString().length());
	
//			if(currentId.equals(id)) {
		//String s = codegen.getpoints();
		 		//add to the base URL
				baseURL = baseURL +"/" + codegen.getcompany();
				baseURL = baseURL +"/" + codegen.getId();
				//add to the base URL
				baseURL = baseURL +"/" + codegen.getpoints();
				baseURL = baseURL +"/" + codegen.getQuantity();
				
				//add points
				outputStream.write(ZXingHelper.getQRCodeImage(baseURL, 200, 200));
				//Write the content to the QR code
				outputStream.flush();
				//Update the Repository

		//outputStream.write(ZXingHelper.getQRCodeImage(baseURL, 200, 200));
		outputStream.flush();
		//Update the Repository
		outputStream.close();
		//Close the Steam
		return map;
	}
	
	
	/**
	   * THIS method sets the given quantity to the correct QR code. 
	   * It does this by going the the Qr Repository and finds the
	   * correct Qr code from the Parameters it was given. 
	   * THIS IS A GET METHOD, Path = product/{company}/{ id}/{ quant}
	   * @param String ID
	   * @param String Company
	   * @param String Quantity
	   * @return Map<String, String>, "verify", "Added" || "verify", "false"
	   */
	 @RequestMapping(value = "/{company}/{ id}/{ quant}", method = RequestMethod.GET)
	    public Map<String, String> setQuantity( @PathVariable("company") String company, @PathVariable(" id") String id, @PathVariable(" quant") String quant) {
	    //THIS METHOD SETS THE QUANTIY OF THE QR CODE AND UPDATES THE REPOISTORY 
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
		
        Iterable<QRcode> yeet = QRservice.findAll(); //gets all the product types of qrcodes
       
		 for (QRcode current : yeet)
		 {
			 String currentCompany = current.getcompany().toString().trim();
			 if(company.toString().trim().equals(currentCompany)) {
				 
				if(current.getId() == currentId1) {
					 
					 map.put("verify", "Added");

	        		 current.setQuantity(currentquantity); //set current points to current user
	        		
	        		 QRrepo.flush(); //update DataBase
	        		 return map;
	        		 
				}

			 }
	           
	    } 

        map.put("verify", "false");
        return map;
    }
	 
	 /**
	   * This can be used once the user scans the code and gets the id and company. This 
	   * will return json data of points to add to user once the user gets back the info
	   * they can confirm then sends another post to the owner repo to update their points.
	   * THIS IS A POST METHOD, Path = product/{company}/{id}
	   * @param String ID
	   * @param String Company
	   * @return HashMap<String, String>, "points", "No More Scans Left" || "points", "Not Found" || ex. "points", "123"
	   */
	 @RequestMapping(value= "/{ company}/{id}", method= RequestMethod.POST) ///{points}/{quantity}
		public HashMap<String, String> findCode(@PathVariable("id") Integer id, 
				@PathVariable(" company") String company,
				@PathVariable("points") String points,
				@PathVariable("quantity") String quantity) {
		//This can be used once the user scans the code and gets the id and company. This will return json data of points to add to user
		 //once the user gets back the info they can confirm then sends another post to the owner repo to update their points.
		
	    	
		 company = company.toString().trim();
	        HashMap<String, String> map = new HashMap<>();
	        int currentId1 = 0;
			 try {
				 currentId1 = id;
			 }
			 catch (NumberFormatException e)
			 {
				 currentId1 = 0; //not found
			 }
			 
			//System.out.println(currentId1);
	        Iterable<QRcode> yeet = QRservice.findAll(); //gets all the product types of qrcodes
	       
			 for (QRcode current : yeet)
			 {
//				 String currentCompany = current.getcompany().toString().trim();
//				 
//				 if(company.toString().trim().equals(currentCompany)) {
					 
					if(current.getId() == currentId1) {
						 	 
						if(current.getQuantity() < 1) {
							
								map.put("points", "No More Scans Left");
						        return map;
						}
						int newquantity = current.getQuantity() -1 ;
						
		        		 current.setQuantity(newquantity); //set current points to current user
		        		 
		        		 
		        		 map.put("points", points);
		        		
		        		
		        		 QRrepo.flush(); //update DataBase
		        		
		        		 return map;
		        		 
					}

				 }
		           
		   // } 

	        map.put("points", "Not Found");
	        return map;
		
		}
	

}
