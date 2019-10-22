package org.springframework.samples.outreach.qr;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.qr.*;

public class QRService {

	@Autowired
	ProductRepository productRepo;
	@Autowired
	private static ProductService productService;
	
	public static void main(String args[]) {
		
	}
	
	public void consume(HashMap<String, String> map) throws Exception {
		
		Iterable<Product> results = productService.findAll();
        QRService test = new QRService();
        test.consume(map);
    	System.out.print(this.getClass().getSimpleName() + "use a code is invoked");
    	Optional<Product> pr1 = productRepo.findById(hashCode());
    	Product p1 = new Product();
    	if(!pr1.isPresent())
    		throw new Exception("could not find qr with id: " + p1.getId());
    	else if(p1.getpoints()==0) 
			System.out.print("there are no remaining codes");
		else
    	System.out.println(this.getClass().getSimpleName() + " 1 code from " + p1.getcompany() + "used");
		
		p1.setQuantity(p1.getQuantity()-1);
	}

	public void consume(String codeToString, String identifierString) {
		// TODO Auto-generated method stub
		
	}
}
