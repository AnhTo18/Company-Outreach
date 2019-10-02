package org.springframework.samples.outreach.owner;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	    
	    /*get qr code by id*/
	    @RequestMapping(method = RequestMethod.GET, path = "/qrcodes/{ownerId}")
	    public Optional<QrCode> findQrCodeById(@PathVariable("ownerId") int id) {
	        logger.info("Entered into Controller Layer");
	        Optional<QrCode> results = qrCodeRepository.findById(id);
	        return results;
	    }
	    
}
