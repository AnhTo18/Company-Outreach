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
package org.springframework.samples.outreach.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.samples.outreach.company.Companies;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.owner.Owners;
import org.springframework.samples.outreach.websockets.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller to map events
 * @author creimers
 * @author kschrock
 */
@RestController
@RequestMapping("events")
class EventController {

    @Autowired
    EventRepository eventRepository;
    
    @Autowired
    CompanyRepository companyRepository;
  

    private final Logger logger = LoggerFactory.getLogger(EventController.class);
    
    /**
	   * This method creates and add an event to the Events Repository.
	   * THIS IS A POST METHOD, Path = /add
	   * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	   */
    
//    @RequestMapping(value= "/{company}/{id}", method= RequestMethod.POST)
//	public HashMap<String, String> findCode(@PathVariable("id") String id, @PathVariable("company") String company ) {
    @RequestMapping(value= "/add/{company}", method= RequestMethod.POST)
	public HashMap<String, String>  createEvent(@PathVariable("company") String company, @RequestBody Events newevent) {
    	 HashMap<String, String> map = new HashMap<>();
    	 ArrayList<String> usersSubbed = new ArrayList<String>();
    	 HelloWorldSocket eventmanager = new HelloWorldSocket();
    	 String eventInfo = "";
    	
    	  Companies current = new Companies();
      //    for(Companies current : results) {
        	  
        	 usersSubbed.addAll(current.getSubscribers());
        	
        //  }
//    	 for(int i=0; i<company.isSubbed.size(); i++){
//    		 UsersSubbed.add(company.subscribers[i]);
//    		 UsersSubbed.add(companyRepository.findAll());
//    	}
    	 try {
    		 eventmanager.onMessage(usersSubbed, eventInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.getClass().getSimpleName() + " - Create new event method is invoked.");
		 eventRepository.save(newevent);
		 map.put("verify", "Added");
		 //test.onMessage(session, message);
		 return map;

	}

    /**
	   * This method finds all the events within the event Repository.
	   * THIS IS A GET METHOD, Path = /events
	   * @return List<Events> This returns the list of events within the Repository.
	   */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Events> getAllEvents() {
        logger.info("Entered into Controller Layer");
        List<Events> results = eventRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    
    /**
	   * This method finds the given Id event object within the event Repository.
	   * THIS IS A GET METHOD, Path = /{event}
	   * @param String event
	   * @return Events This returns the single event by id within the Repository.
	   */
  @RequestMapping(method = RequestMethod.GET, path = "/{username}")
  public Events findEventById(@PathVariable("username") String username) {
      logger.info("Entered into Controller Layer");
    //  Optional<Events> results = EventsRepository.findById(id);j
      List<Events> results = eventRepository.findAll();
      username = username.toString().trim();
      for(Events current : results) {
      	
      	if(current.getEventName().trim().equals(username)) {
      		return current;
      	}
      	
      }
      return null; //NOT FOUND
  }
  
    /**
	   * This method deletes all the event objects made by a specific company.
	   * requires confirmation of deletion
	   * THIS IS A POST METHOD, Path = /deleteall
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, path= "/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all events is invoked.");
		boolean confirmed = false;
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		if(input == "yes" || input == "y") {
			confirmed =true;
		}
		if(confirmed)
		eventRepository.deleteAll();
		else if(!confirmed)
			System.out.print("okay");
		sc.close();
	}
    
    /**
	   * This method deletes the event object by ID within the Event Repository.
	   * THIS IS A POST METHOD, Path = /delete/{id}
	   * @param int ID
	   * @return void
	   */
    @RequestMapping( method= RequestMethod.POST, value= "/delete/{id}")
	public void deleteEventById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete event by id is invoked.");

		Optional<Events> event =  eventRepository.findById(id);
		if(!event.isPresent())
			throw new Exception("Could not find event with id- " + id);

		eventRepository.deleteById(id);
	}

}
