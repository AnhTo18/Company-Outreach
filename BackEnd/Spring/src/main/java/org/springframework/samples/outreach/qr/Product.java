package org.springframework.samples.outreach.qr;

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
 * Simple JavaBean domain object representing an Product.
 * This contains the fields to create an QR code.
 * @author creimers
 * @author kschrock
 */
@Entity
@Table(name = "product")
public class Product {

	@Id
	@Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
	private Integer id;
	@Column(name = "company")
    @NotFound(action = NotFoundAction.IGNORE)
	private String company;
	@Column(name = "points")
    @NotFound(action = NotFoundAction.IGNORE)
	private double points;
	@Column(name = "quantity")
    @NotFound(action = NotFoundAction.IGNORE)
	private int quantity;

	public Integer getId() {
		return id;
	}

	@Column
	public void setId(Integer id) {
		this.id = id;
	}

	public String getcompany() {
		return company;
	}

	public void setcompany(String company) {
		this.company = company;
	}

	public double getpoints() {
		return points;
	}

	public void setpoints(double points) {
		this.points = points;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	 @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("id", this.getId())
	                .append("company", this.getcompany())
	                .append("points", this.getpoints())
	                .append("quantity", this.getQuantity()).toString();
	 }
}
