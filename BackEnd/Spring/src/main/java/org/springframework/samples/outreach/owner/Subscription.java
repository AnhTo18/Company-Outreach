package org.springframework.samples.outreach.owner;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.outreach.company.Company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Subscription {

	@ManyToOne(fetch = FetchType.EAGER, cascade = {
    		CascadeType.PERSIST,
    		CascadeType.MERGE
    })
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties("subscriptions") // prevent circular dependency with JSON deserializing
	private Owner user;
	
	  @ManyToOne(fetch = FetchType.EAGER, cascade = {
	    		CascadeType.PERSIST,
	    		CascadeType.MERGE
	    })
	    @NotFound(action = NotFoundAction.IGNORE)
	    @JsonIgnoreProperties("subscriptions") // prevent circular dependency with JSON deserializing
	private Company company;
	
	private int points;
	
	
}
