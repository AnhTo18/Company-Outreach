package org.springframework.samples.outreach.owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "qrcode")
public class QrCode {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;
	 
	 @Column(name = "points")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer points;
	 
	 @Column(name = "company")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String company;
	 
	 
	 public Integer getId() {
	        return id;
	        //Getter for ID of QR code
	    }

	 public void setId(Integer id) {
	        this.id = id;
	        //Setter for ID of QR code
	    }
	 
	 public Integer getPoints() {
	        return points;
	        //Getter for points of code
	    }

	 public void setPoints(Integer points) {
	        this.points = points;
	        //Setter for points of code
	    }

	 public String getCompany() {
	        return company;
	        //Getter for company of code
	    }

	 public void setCompany(String company) {
	        this.company = company;
	        //Setter for company of code
	    }

}
