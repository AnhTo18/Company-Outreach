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

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.json.JSONObject;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.outreach.*;
import io.micrometer.core.lang.NonNull;

/**
 * Simple JavaBean domain object representing an event.
 * This contains the fields to create an event.
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "company_events")
public class Event {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	 	@Column(name = "event_name")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String eventname;
	 
	    @Column(name = "location")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String location;

	    @Column(name = "company_name")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String companyname;

	    @Column(name = "date")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String date;
	    
	    @Column(name = "time")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String time;

//	    @Column(name = "info")
//	    @Convert(converter= JSONObjectConverter.class)
//	    @NotFound(action = NotFoundAction.IGNORE)
//	    private JSONObject jsonData;

	    public Integer getId() {
	        return id;
	        //Getter for ID of User
	    }

	    public void setId(Integer id) {
	        this.id = id;
	        //Setter for ID of User
	    }

	    public boolean isNew() {
	        return this.id == null;
	    }
	    
	    public String getEventName() {
	        return this.eventname;
	        //Getter for location of the event
	    }

	    public void setEventName(String eventname) {
	        this.eventname = eventname;
	      //Setter for location of the event
	    }
	    
	    public String getLocation() {
	        return this.location;
	        //Getter for location of the event
	    }

	    public void setLocation(String location) {
	        this.location = location;
	      //Setter for location of the event
	    }
	    
	    public String getCompanyName() {
	        return this.companyname;
	        //Getter for Company posting the event
	    }

	    public void setCompanyName(String company) {
	        this.companyname = company;
	        //Setter for Company posting the event
	    }

	    public String getDate() {
	        return this.date;
	        //Getter for event time
	    }

	    public void setDate(String date) {
	        this.date = date;
	        //Setter for event time
	    }
	    
	    public String getTime() {
	        return this.time;
	        //Getter for event time
	    }

	    public void setTime(String time) {
	        this.time = time;
	        //Setter for event time
	    }
	    
//	    public JSONObject getinfo() {
//	    	return this.jsonData;
//	    }
//	    
//	    public void setinfo(JSONObject info) {
//	    	this.jsonData = info;
//	    }
	    
	    @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("ID", this.getId())
	                .append("new", this.isNew())
	                .append("company", this.getCompanyName())
	                .append("event", this.getEventName())
	                .append("location", this.getLocation())
	                .append("date" , this.getDate())
	                .append("time", this.getTime()).toString();
	        		
//	        		 .append("ID", this.getId())
//		                .append("event_info", this.getinfo()).toString();
	    }
	
	
}