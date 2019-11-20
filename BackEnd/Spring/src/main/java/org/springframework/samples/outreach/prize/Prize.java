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
package org.springframework.samples.outreach.prize;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.events.Event;
import org.springframework.samples.outreach.owner.Owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simple JavaBean domain object representing an owner.
 * This contains the fields to create an owner.
 * @author creimers
 * @author kschrock
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name = "prize")
public class Prize {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	    @Column(name = "prize_name")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String prizeName;

	    @Column(name = "cost")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String cost;

	    @Column(name = "qty")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String qty;

	    @Column(name = "color") //perhaps N/A
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String color ="N/A";
	    
	    @ManyToMany(mappedBy = "prizes", fetch = FetchType.EAGER, cascade = {
	    		CascadeType.PERSIST,
	    		CascadeType.MERGE
	    })
	    @NotFound(action = NotFoundAction.IGNORE)
	    @JsonIgnoreProperties("prizes") // prevent circular dependency with JSON deserializing
	   	private Set<Company> company;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getPrizeName() {
			return prizeName;
		}

		public void setPrizeName(String prizeName) {
			this.prizeName = prizeName;
		}

		public String getCost() {
			return cost;
		}

		public void setCost(String cost) {
			this.cost = cost;
		}

		public String getQty() {
			return qty;
		}

		public void setQty(String qty) {
			this.qty = qty;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public Set<Company> getCompany() {
			return company;
		}

		public void setCompany(Set<Company> company) {
			this.company = company;
		}

}