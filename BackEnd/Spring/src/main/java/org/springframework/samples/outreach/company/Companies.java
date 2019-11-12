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
package org.springframework.samples.outreach.company;

import java.util.ArrayList;

import javax.persistence.Column;
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
import org.springframework.core.style.ToStringCreator;

/**
 * Simple JavaBean domain object representing an owner.
 * This contains the fields to create an owner.
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "companies")
public class Companies {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	    @Column(name = "company_name")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String companyName;

	    @Column(name = "address")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String address;
	    
	    @Column(name = "user_name")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String username;

	    @Column(name = "telephone")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String telephone;

	    @Column(name = "pass_word")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String password;
	    
	    @Column(name = "subscribers")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private ArrayList<String> subscribers;
	    
	    @Column(name = "isPaid")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private boolean isPaid;

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

	    public String getCompanyName() {
	        return this.companyName;
	        //Getter for FirstName of User
	    }

	    public void setCompanyName(String companyName) {
	        this.companyName = companyName;
	        //Setter for FirstName of User
	    }

	    public String getAddress() {
	        return this.address;
	        //Getter for Address of User
	    }

	    public void setAddress(String address) {
	        this.address = address;
	        //Setter for Address
	    }

	    public String getTelephone() {
	        return this.telephone;
	        //Getter for Telephone Number
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	        //Setter for Telephone Number
	    }
	    
	    public String getpassword() {
	        return this.password;
	        //Getter for password
	    }

	    public void setPassord(String password) {
	        this.password = password;
	        //Setter for password
	    }
	    
	    public boolean getPaidStatus() {
	        return this.isPaid;
	        //gets status of company payment
	    }

	    public void setPaidStatus(boolean isPaid) {
	        this.isPaid = isPaid;
	        //Sets the companies paid status to true or false
	    }
	    
	    public ArrayList<String> getSubscribers() {
	        return this.subscribers;
	        //gets status of company payment
	    }

	    public void addSubscriber(String name) {
	        this.subscribers.add(name);
	        //Sets the companies paid status to true or false
	    }
	
	    public String getUsername() {
	        return this.username;
	        //Getter for username
	    }

	    public void setUsername(String username) {
	        this.username = username;
	        //Setter for username
	    }
	    
	    @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("id", this.getId())
	                .append("new", this.isNew())
	                .append("companyName", this.getCompanyName())
	                .append("userName", this.getUsername())
	                .append("address", this.address)
	                .append("isPaid", this.getPaidStatus())
	                .append("subscribers",this.getSubscribers())
	                .append("telephone", this.telephone).toString();
	    }
	
	
}