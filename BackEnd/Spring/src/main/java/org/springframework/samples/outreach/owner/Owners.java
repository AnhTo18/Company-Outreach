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
package org.springframework.samples.outreach.owner;

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
 *
 */
@Entity
@Table(name = "profile")
public class Owners {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	    @Column(name = "first_name")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String firstName;

	    @Column(name = "last_name")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String lastName;

	    @Column(name = "address")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String address;

	    @Column(name = "telephone")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String telephone;

	    @Column(name = "user_name")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String username;

	    @Column(name = "pass_word")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String password;
	    
	    @Column(name = "points")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String points;
	    
//	    public Owners(int id, String FirstName, String LastName, String Address, int points, String telephone) {
//			// TODO Auto-generated constructor stub
//	    	this.id = id;
//	    	this.firstName = FirstName;
//	    	this.lastName = LastName;
//	    	this.address = Address;
//	    	this.points = points + "";
//	    	this.telephone = telephone;
//		}


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

	    public String getFirstName() {
	        return this.firstName;
	        //Getter for FirstName of User
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	        //Setter for FirstName of User
	    }

	    public String getLastName() {
	        return this.lastName;
	        //Getter for LastName of User
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	      //Setter for LastName of User
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
	    public String getUsername() {
	        return this.username;
	        //Getter for username
	    }

	    public void setUsername(String username) {
	        this.username = username;
	        //Setter for username
	    }
	    
	    public String getpassword() {
	        return this.password;
	        //Getter for password
	    }

	    public void setPassord(String password) {
	        this.password = password;
	        //Setter for password
	    }
	    
	    public String getPoints() {
	        return this.points;
	        //Getter for password
	    }

	    public void setPoints(String points) {
	        this.points = points;
	        //Setter for password
	    }
	    @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("id", this.getId())
	                .append("new", this.isNew())
	                .append("lastName", this.getLastName())
	                .append("firstName", this.getFirstName())
	                .append("address", this.address)
	                .append("points" , this.getPoints())
	                .append("telephone", this.telephone).toString();
	    }
	
	
}