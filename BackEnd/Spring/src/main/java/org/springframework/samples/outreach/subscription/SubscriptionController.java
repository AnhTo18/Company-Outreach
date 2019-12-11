package org.springframework.samples.outreach.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.events.Event;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subscription")
public class SubscriptionController {
	
	@Autowired
	SubscriptionService service;

    @RequestMapping(method = RequestMethod.GET, path = "/unsubscribe")
    public void unsub(@RequestBody Owner owner, @RequestBody Company company) {
    	
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/subscribe")
    public void sub(@RequestBody Owner owner, @RequestBody Company company) {
    	service.subscribeToCompany(owner, company);
    }
}
