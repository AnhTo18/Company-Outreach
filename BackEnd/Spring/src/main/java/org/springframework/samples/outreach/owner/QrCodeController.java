package org.springframework.samples.outreach.owner;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RequestBody;
>>>>>>> kschrock
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrCodeController {

	 @Autowired
	    QrCodeRepository qrCodeRepository;
	 
	 private final Logger logger = LoggerFactory.getLogger(QrCodeController.class);
	 
	 
	 /*get all qr codes*/
	    @RequestMapping(method = RequestMethod.GET, path = "/qrcodes")
	    public List<QrCode> getAllQrCodess() {
	        logger.info("Entered into Controller Layer");
	        List<QrCode> results = qrCodeRepository.findAll();
	        logger.info("Number of Records Fetched:" + results.size());
	        return results;
	    }
	    
<<<<<<< HEAD
	    /*get qr code by id*/
	    @RequestMapping(method = RequestMethod.GET, path = "/qrcodes/{ownerId}")
	    public Optional<QrCode> findQrCodeById(@PathVariable("ownerId") int id) {
=======
	    /*get qr code by id
	     * This would be used for when they scan the QR code they can look it up for points
	     * this in the future should return JSON format for points.
	     * */
	    @RequestMapping(method = RequestMethod.GET, path = "/qrcodes/{ownerId}")
	    public Optional<QrCode> findQrCodeById(@PathVariable("qrcodeId") int id) {
>>>>>>> kschrock
	        logger.info("Entered into Controller Layer");
	        Optional<QrCode> results = qrCodeRepository.findById(id);
	        return results;
	    }
	    
<<<<<<< HEAD
=======
	    //Admin use for creating QR Codes
	    @RequestMapping(value= "/qrcodes/create", method= RequestMethod.POST)
		public String createEmployee(@RequestBody QrCode code) {
			System.out.println(this.getClass().getSimpleName() + " - Create new employee method is invoked.");
			 qrCodeRepository.save(code);
			 return "New QrCode by " + code.getCompany() + "Saved";
		}
>>>>>>> kschrock
}
