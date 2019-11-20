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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.outreach.company.Company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Simple JavaBean domain object representing an owner.
 * This contains the fields to create an owner.
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "owner")
public class Owner {

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
	    
	    @ManyToMany(fetch = FetchType.EAGER, cascade = {
	    		CascadeType.PERSIST,
	    		CascadeType.MERGE
	    })
	    @NotFound(action = NotFoundAction.IGNORE)
	    @JsonIgnoreProperties("owners") // prevent circular dependency with JSON deserializing
	    private List<Company> companies;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}

		public List<Company> getCompanies() {
			return companies;
		}

		public void setCompanies(List<Company> companies) {
			this.companies = companies;
		}
	
}