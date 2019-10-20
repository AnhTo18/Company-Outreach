package org.springframework.samples.outreach.qr;

import java.io.OutputStream;
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
    @RequestMapping( method= RequestMethod.POST, value= "/qrcode/setqty/{id}")
	public void SetQTY(@PathVariable int id, @RequestParam int quantity) throws Exception {
    	System.out.print(this.getClass().getSimpleName() + "setting quantity of codes is invoked");
    	Optional<Product> pr1 = productRepo.findById(id);
    	Product p1 = new Product();
    	if(!pr1.isPresent())
    		throw new Exception("could not find qr with id: " + p1.getId());
		else
    	System.out.println(this.getClass().getSimpleName() + "setting qr quantity to: "
    			+ quantity);
		
		p1.setQuantity(quantity);
	}
	
    //Consume a code
    @RequestMapping( method= RequestMethod.POST, value= "/qrcode/use/{id}")
	public void useCode(@PathVariable int id) throws Exception {
    	System.out.print(this.getClass().getSimpleName() + "use a code is invoked");
    	Optional<Product> pr1 = productRepo.findById(id);
    	Product p1 = new Product();
    	if(!pr1.isPresent())
    		throw new Exception("could not find qr with id: " + p1.getId());
    	else if(p1.getpoints()==0) 
			System.out.print("there are no remaining codes");
		else
    	System.out.println(this.getClass().getSimpleName() + " 1 code from " + p1.getcompany() + "used");
		
		p1.setQuantity(p1.getQuantity()-1);
	}

}