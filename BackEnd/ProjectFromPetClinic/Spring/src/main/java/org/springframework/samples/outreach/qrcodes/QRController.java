package org.springframework.samples.outreach.qrcodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.qrcodes.QRController;
import org.springframework.samples.outreach.qrcodes.QRCodes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.acl.Owner;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
class QRController {
	
	@Autowired
	    QRRepository qrRepository;
	    
	  

	  private final Logger logger= LoggerFactory.getLogger(QRController.class);

	    /*to save an users*/
//	    @PostMapping( path = "/owners/new")
//	    public String saveOwner(Owners owner) {
//	        ownersRepository.save(owner);
//	        return "New Owner "+ owner.getFirstName() + " Saved";
//	    }
	//to  be continued

}
