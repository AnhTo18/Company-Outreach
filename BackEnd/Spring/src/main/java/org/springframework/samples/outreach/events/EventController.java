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
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.OwnerRepository;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.samples.outreach.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller to map events
 * 
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
	
	@Autowired
	OwnerRepository ownerRepository;

	private final Logger logger = LoggerFactory.getLogger(EventController.class);

	/**
     * This method gets all the Events for a specific company in the Event Repository if a company requests it.
     * otherwise if it is a standard user then it will retrieve a list of all companies they are subscribed to
     * so that they can view their events
     * THIS IS A GET METHOD, Path = /events/getAll
     * @return Iterable<Product> This returns the list of Event Objects.
     */
      @RequestMapping(method = RequestMethod.GET, path = "/getAll")
      public List<?> getAllCompCodes(@RequestBody Event[] event) {

          Iterable<Event> compResults = eventRepository.findAll();
          List<Event> events = new ArrayList<Event>();
          //if the events are created by a company then proceed with retrieving all of their created events
          if(event[1].getUsername().equals("company")) {
          for(Event current : compResults) {
              //if event is the companies, add it to the list
              if(current.getCompanyname().equals(event[0].getUsername())) {
                  events.add(current);
              }
          }
          //return the list of events the company has made (may be empty)
          return events;
          }
          //otherwise, since its an owner, then retrieve a list of companies they are subscribed to
          else{
              Iterable<Owner> ownerResults = ownerRepository.findAll();
              //search through all owners
              for(Owner current: ownerResults) {
//if the user matches the one passed to us
                  if(current.getUsername().equals(event[0].getUsername())) {
                      //create a list of all the companies
                      List<Company> allcomps = companyRepository.findAll();
                      //build a list of companies the user is subscribed to
                      List<Company> ownersubs = new ArrayList<Company>();

                      for(Company currentcomp: allcomps) {
                          // if the comp has a subscriber with the the username passed to us
                          if(currentcomp.getSubscriptions().iterator().hasNext()) {
                          if(currentcomp.getSubscriptions().iterator().next().getOwner().getUsername().equals(event[0].getUsername())) {
                              //add them to the list of subscriptions the owner has
                              ownersubs.add(currentcomp);
                              }
                          }
                      }
                      //return a list of all companies that the user has subscribed to
                      return ownersubs;
                  }
              }
          }
          //otherwise return nothing
          return null;
      }


	/**
	 * This method finds all the events within the event Repository. THIS IS A GET
	 * METHOD, Path = /events
	 * 
	 * @return List<Events> This returns the list of events within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Event> getAllEvents() {
		logger.info("Entered into Controller Layer");
		List<Event> results = eventRepository.findAll();
		// find all the events
		logger.info("Number of Records Fetched:" + results.size());
		// print to console the number of events
		return results;
		// return all the events
	}

	/**
	 * This method finds the given Id event object within the event Repository. THIS
	 * IS A GET METHOD, Path = /{event}
	 * 
	 * @param String event
	 * @return Events This returns the single event by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{eventname}")
	public Event findEventById(@PathVariable("eventname") String eventname) {
		logger.info("Entered into Controller Layer");
		List<Event> results = eventRepository.findAll();
		// find all events
		eventname = eventname.toString().trim();
		// given event name
		for (Event current : results) {
			// iterate through all events
			if (current.getEventname().trim().equals(eventname)) {
				// if the event name matches the given event name
				return current;
				// return the current event
			}

		}
		return null;
		// NOT FOUND
	}

	/**
	 * This method deletes all the event objects made by a specific company.
	 * requires confirmation of deletion THIS IS A POST METHOD, Path = /deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all events is invoked.");
		// print to the console
		eventRepository.deleteAll();
		// this deletes tall the event objects in the repo
	}

	/**
	 * This method deletes the event object by ID within the Event Repository. THIS
	 * IS A POST METHOD, Path = /delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/delete/{id}")
	public void deleteEventById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete event by id is invoked.");
		// print to the console

		Optional<Event> event = eventRepository.findById(id);
		// find the event by the ID
		if (!event.isPresent())
			// this checks if the event is in the repository
			throw new Exception("Could not find event with id- " + id);

		eventRepository.deleteById(id);
		// delete the event by ID
	}

}
