package org.springframework.samples.outreach.subscription;

import java.util.HashMap;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.owner.Owner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Subscription {

	@ManyToOne(fetch = FetchType.EAGER, cascade = {
    		CascadeType.PERSIST,
    		CascadeType.MERGE
    })
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties("subscriptions") // prevent circular dependency with JSON deserializing
	private Set<Owner> user;
	
	  @ManyToOne(fetch = FetchType.EAGER, cascade = {
	    		CascadeType.PERSIST,
	    		CascadeType.MERGE
	    })
	    @NotFound(action = NotFoundAction.IGNORE)
	    @JsonIgnoreProperties("subscriptions") // prevent circular dependency with JSON deserializing
	private Set<Company> company;
	
	private int points;
	
	
	   public Set<Company> getCompanies() {
		   //java util for set
	        return this.company;
	        //Getter for password
	    }
   
	   public Set<Owner> getOwners() {
	        return this.user;
//	        //gets status of company payment
	    }
	   
	   public int getpoints() {
	        return this.points;
//	        //gets status of points
	    }
	
	
}
