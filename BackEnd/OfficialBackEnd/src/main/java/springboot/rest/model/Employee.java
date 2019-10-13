package springboot.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Component;

@Component

// Spring jpa jars.
@Entity
@Table(name= "testing")

// To increase speed and save sql statement execution time.
@DynamicInsert
@DynamicUpdate
public class Employee {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private int id;
	
	@Column(name = "first_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String first_name;
	
	@Column(name = "last_name")
	@NotFound(action = NotFoundAction.IGNORE)
	private String last_name;
	
	@Column(name = "address")
	@NotFound(action = NotFoundAction.IGNORE)
	private String address;
	
	@Column(name = "telephone")
	@NotFound(action = NotFoundAction.IGNORE)
	private String telephone;
	
	@Column(name = "points")
	@NotFound(action = NotFoundAction.IGNORE)
	private int points;
	
	@Column(name = "username")
	@NotFound(action = NotFoundAction.IGNORE)
	private String username;
	
	@Column(name = "password")
	@NotFound(action = NotFoundAction.IGNORE)
	private String password;

	public Employee() { }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + first_name + last_name + ", address=" + address + ", telephone=" + telephone + ", points=" + points +", username=" + username + ", password=" + password + "]";
	}

}